# ToDoList 
Mобильное приложение , представляющее из себя ежедневник.

В приложении присутствуют 2 экрана:
- Календарь со спискам дел на выбранную дату;
- Карточка создания/редактирования дела, с возможностью указать наименование дела, его описание, время старта и окончания.

# Скриншоты приложения
| 1 Экран | 1 Экран | 2 Экран | 2 Экран |
| ------------- | ------------- | ------------- | ------------- |
| ![ToDoListFragment1](https://github.com/BrovchenkoDmitriy/ToDoList/assets/99586855/f7c0b2a3-7abe-4871-9829-9300bf5b7a33) | ![ToDoListFragment2](https://github.com/BrovchenkoDmitriy/ToDoList/assets/99586855/71454809-5074-4a27-aaed-9d97d3f527f1) | ![TaskItemFragment1](https://github.com/BrovchenkoDmitriy/ToDoList/assets/99586855/e9634b7c-8d8f-44ab-8013-44cd21f60229)  |![TaskItemFragment2](https://github.com/BrovchenkoDmitriy/ToDoList/assets/99586855/53c30ae1-89c3-46e8-a17a-f798f756dd06) |

# Описание
> ### Календарь со списком дел:
>> + Календарь выбора даты
>> + Таблица дел
> ### Карточка добавления/редактирования дела:
>> + Поле для ввода названия дела
>> + Поле для ввода описания дела
>> + Выбор даты начала/окончания дела
>> + Кнопки подтверждения и отмены создания дела

# Примечания
> + списка дел реализован через кастомную ViewGroup
> + отображаемые дела в списке реализованы через кастомные View
> + для просмотра деталей дела (или редактирования), необходимо кликнуть по нему в списке дел.
> + для удаление дела - долгий клик по нему в списке дел,
> + в проекте применяются: Room, Dagger2, Kotlin Coroutines,  Navigation component, MVVM, Clean Architecture.
