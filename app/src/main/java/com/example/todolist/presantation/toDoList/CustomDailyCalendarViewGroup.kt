package com.example.todolist.presantation.toDoList

import android.animation.LayoutTransition
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.todolist.R
import com.example.todolist.domain.TaskItem
import kotlin.math.roundToInt

class CustomDailyCalendarViewGroup : ViewGroup {
    var eachHourHeightInDp = 108f
    var minimumHeightEachSellPercentage = .25f
    private var maxChildrenEnd = 0
    private var numberOfRows = 24
    private var dividerStartOffset = 70f
    private lateinit var dividerPaint: Paint
    private lateinit var textPaint: Paint
    private var dividerColorId: Int = R.color.divider_color
    private var dividerTextColorId: Int = R.color.title_color
    private lateinit var dividerTitles: MutableList<String>


    constructor(context: Context?) : super(context) {
        init(null)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
    }


    @SuppressLint("CustomViewStyleable")
    private fun init(set: AttributeSet?) {

        setWillNotDraw(false)
        layoutTransition = LayoutTransition()
        //if user not set any divider array
        dividerTitles = mutableListOf()
        for (i in 0 until numberOfRows step 1) {

            dividerTitles.add(
                when (i) {
                    in 0 until 10 -> {
                        "0$i:00"
                    }

                    in 10..24 -> {
                        "${i}:00"
                    }

                    else -> {
                        ""
                    }
                }

            )
        }


        if (set == null) {

            dividerPaint = Paint().apply {
                strokeWidth = 1f.toPx().toFloat()
                style = Paint.Style.FILL
                color = ContextCompat.getColor(context, dividerColorId)
            }

            textPaint = Paint().apply {
                color = ContextCompat.getColor(context, dividerTextColorId)
                textSize = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 16f,
                    resources.displayMetrics
                )
            }

            return
        }

        val ta = context.obtainStyledAttributes(set, R.styleable.CustomCalendarGroup)
        eachHourHeightInDp = ta.getDimension(R.styleable.CustomCalendarGroup_eachRowHeight, 108f)
        minimumHeightEachSellPercentage =
            ta.getFloat(R.styleable.CustomCalendarGroup_minimumPercentage, .25f)
        numberOfRows = ta.getInteger(R.styleable.CustomCalendarGroup_numberOfRows, 24)
        dividerStartOffset =
            ta.getDimension(R.styleable.CustomCalendarGroup_dividerStartOffset, 70f)
        ta.recycle()

        dividerPaint = Paint().apply {
            strokeWidth = 1f.toPx().toFloat()
            style = Paint.Style.FILL
            @SuppressLint("ResourceAsColor")
            color = dividerColorId
        }

        textPaint = Paint().apply {
            @SuppressLint("ResourceAsColor")
            color = dividerTextColorId
            textSize = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 16f,
                resources.displayMetrics
            )
        }
    }

    @SuppressLint("ResourceAsColor")
    fun addTask(taskItem: TaskItem) {
        val taskView = TaskView(context, taskItem)
        taskView.setBackgroundResource(R.drawable.bg_task_view)
        addView(taskView)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        post {
            if (childCount > 0)
                getChildAt(childCount - 1).visibility = View.VISIBLE
        }
        measureChildren(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(
            resources.displayMetrics.widthPixels.coerceAtLeast((maxChildrenEnd)),
            eachHourHeightInDp.toPx() * numberOfRows
        )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawDividersAndTimeCaption(canvas)
    }

    private fun drawDividersAndTimeCaption(canvas: Canvas) {
        val eachHourHeightInPx = eachHourHeightInDp.toPx()
        val dividerInPx = dividerStartOffset.toPx().toFloat()

        for (i in 0 until numberOfRows step 1) {
            val y = i * eachHourHeightInPx.toFloat()
            val rect = Rect()
            textPaint.getTextBounds(dividerTitles[i], 0, dividerTitles[i].length, rect)

            canvas.drawText(
                dividerTitles[i], (dividerInPx - rect.width()) / 2,
                y + eachHourHeightInPx / 2 + rect.height() / 2, textPaint
            )

            canvas.drawLine(0f, y, measuredWidth.toFloat(), y, dividerPaint)

        }
    }


    override fun onLayout(p0: Boolean, p1: Int, p2: Int, p3: Int, p4: Int) {
        maxChildrenEnd = 0
        val dividerStartOffsetpx = dividerStartOffset.toPx()
        for (i in 0 until childCount) {
            val child = getChildAt(i) as TaskView
            child.top = convertMinuteToPx(child.getTaskTime().first)
            child.left = calculateNewChildPlaceLeft(i, child)
//            offset for divider applied
            child.left = if (child.left < dividerStartOffsetpx) {
                dividerStartOffsetpx
            } else {
                child.left
            }
            child.layout(
                child.left, child.top,
                child.left + child.measuredWidth,
                child.top + child.measuredHeight
            )

            if (child.right > maxChildrenEnd)
                maxChildrenEnd = child.right
        }
        post {
            requestLayout()
        }
    }

    private fun calculateNewChildPlaceLeft(index: Int, child: TaskView): Int {
        for (i in 0 until index) {
            val childToCheckForSpace = getChildAt(i) as TaskView

            //if there is no height collision we can skip to another child to check
            if (!hasCommonTime(child.getTaskTime(), childToCheckForSpace.getTaskTime())
                || child.left + child.measuredWidth < childToCheckForSpace.left
            )
                continue

            //shift right when detect width collision and check again for new changes
            if (child.left < childToCheckForSpace.right) {
                child.left = childToCheckForSpace.right
                calculateNewChildPlaceLeft(index, child)
            }
        }
        return child.left
    }


    private fun hasCommonTime(
        firstRange: Pair<Float, Float>,
        secondRange: Pair<Float, Float>
    ): Boolean =
        !(firstRange.first >= secondRange.second || firstRange.second <= secondRange.first)


    private fun convertMinuteToPx(startTime: Float) =
        (startTime * eachHourHeightInDp.toPx()).roundToInt()


    override fun generateLayoutParams(attrs: AttributeSet): LayoutParams {
        return LayoutParams(context, attrs)
    }

    override fun generateDefaultLayoutParams(): LayoutParams {
        return LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun generateLayoutParams(p: ViewGroup.LayoutParams): ViewGroup.LayoutParams {
        return LayoutParams(p)
    }

    override fun checkLayoutParams(p: ViewGroup.LayoutParams): Boolean {
        return p is LayoutParams
    }

    /**
     * Custom per-child layout information.
     */
    class LayoutParams : MarginLayoutParams {
        /**
         * The gravity to apply with the View to which these layout parameters
         * are associated.
         */

        constructor(c: Context, attrs: AttributeSet) : super(c, attrs)

        constructor(width: Int, height: Int) : super(width, height)

        constructor(source: ViewGroup.LayoutParams) : super(source)

    }

    private fun Float.toPx(): Int {
        val r = resources
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, r.displayMetrics)
            .toInt()
    }


}