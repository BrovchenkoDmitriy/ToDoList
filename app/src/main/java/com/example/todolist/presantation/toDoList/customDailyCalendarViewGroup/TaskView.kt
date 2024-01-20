package com.example.todolist.presantation.toDoList.customDailyCalendarViewGroup

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.icu.text.SimpleDateFormat
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import androidx.core.content.withStyledAttributes
import com.example.todolist.R
import com.example.todolist.domain.TaskItem
import java.util.Calendar
import java.util.Locale
import kotlin.math.roundToInt

class TaskView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(
    context, attrs,
) {
    private val format = SimpleDateFormat("HH:mm", Locale.getDefault())


    private var startTime = 0f
    private var endTime = 0f
    private var marginBetweenItems: Int =  16
    var taskItemId: Int = 0
    private var textToDraw = ""
    private var textToDrawTime = ""
    private val textBounds = Rect()

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 16f.sp(context)
        color = Color.WHITE
    }

    constructor(context: Context, taskItem: TaskItem) : this(context) {
        taskItemId = taskItem.id
        val calendar = Calendar.getInstance()
         calendar.timeInMillis = taskItem.dateStart
        this.startTime = calendar.get(Calendar.HOUR_OF_DAY).toFloat() + calendar.get(Calendar.MINUTE).toFloat()/60
        calendar.timeInMillis = taskItem.dateFinish
        this.endTime = calendar.get(Calendar.HOUR_OF_DAY).toFloat() + calendar.get(Calendar.MINUTE).toFloat()/60
        this.textToDraw = taskItem.name
        this.textToDrawTime = format.format(taskItem.dateStart)
    }

    init {
        setPadding(marginBetweenItems, marginBetweenItems, marginBetweenItems, marginBetweenItems)
        context.withStyledAttributes(attrs, R.styleable.TaskView) {
            textToDraw = this.getString(R.styleable.TaskView_taskName)?:""
            textToDrawTime = this.getString(R.styleable.TaskView_taskDateStart)?:""
            startTime = this.getFloat(R.styleable.TaskView_startTime, 0f)
            endTime = this.getFloat(R.styleable.TaskView_endTime, 0f)
        }
    }
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val title = textToDraw.length
        val time = textToDrawTime.length
        if (title >= time) {
            textPaint.getTextBounds(textToDraw, 0, textToDraw.length, textBounds)
        } else
            textPaint.getTextBounds(textToDrawTime, 0, textToDrawTime.length, textBounds)

        val textWidth = textBounds.width()

        val measuredWidth = resolveSize(textWidth + paddingLeft + paddingRight, widthMeasureSpec)

        val oneHourHeight = ((parent as CustomDailyCalendarViewGroup).eachHourHeightInDp).toPx()
        val minDelta = ((parent as CustomDailyCalendarViewGroup).minimumHeightEachSellPercentage)
            //Расчет высоты вьюшки
        val calculatedHeight: Float = if (endTime - startTime < minDelta) {
            textBounds.height()*2.toFloat() // Если высота слишком мала, устанавливаем высоту вмещающую текст
        } else {
            (endTime - startTime) * oneHourHeight
        }
        setMeasuredDimension(
            measuredWidth,
            MeasureSpec.makeMeasureSpec(calculatedHeight.roundToInt(), MeasureSpec.EXACTLY)
        )
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawText(
            textToDraw,
            paddingLeft.toFloat(),
            textBounds.height().toFloat() + paddingTop,
            textPaint
        )
        canvas.drawText(
            textToDrawTime,
            paddingLeft.toFloat(),
            textBounds.height().toFloat() * 2 + 20,
            textPaint
        )
    }

    fun getTaskTime() = startTime to endTime

    private fun Float.toPx(): Int {
        val r = resources
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, r.displayMetrics)
            .toInt()
    }

    private fun Float.sp(context: Context) = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        this,
        context.resources.displayMetrics
    )
}