
# Bin Application

This is a sample Android application that demonstrates a simple shopping cart functionality.

## Architecture

The project follows the Model-View-ViewModel (MVVM) architecture pattern.

*   **View**: The UI is built using `BinFragment`, which is responsible for displaying the shopping cart items and handling user interactions. It observes `BinViewModel` for data updates.
*   **ViewModel**: `BinViewModel` acts as a bridge between the View and the Model. It holds the business logic, fetches data from the `BinRepository`, and exposes it to the View using `LiveData`.
*   **Model**: The data layer is composed of:
    *   `BinRepository`: An interface that defines the data operations.
    *   `BinRepositoryImpl`: The implementation of `BinRepository`. It uses two data sources: `BinDataSourceProducts` and `BinDataSourceCache`.
    *   `BinDataSourceProducts`: Fetches product data from a local JSON file (`tovary_i_kategorii.json`).
    *   `BinDataSourceCache`: Caches the product data using `SharedPreferences` for persistence.

## Dependencies

The project uses the following key libraries:

*   **AndroidX Libraries**:
    *   `appcompat`: For backward compatibility of Material Design components.
    *   `material`: For Material Design components.
    *   `activity`: For managing activities.
    *   `constraintlayout`: For building complex layouts.
    *   `lifecycle`: For managing UI component lifecycle.
    *   `recyclerview`: For displaying lists of items.
*   **UI**:
    *   `glide`: For image loading and caching.
    *   `ViewBinding`: To easily interact with views.
*   **Data**:
    *   `gson`: For JSON serialization and deserialization.
*   **Testing**:
    *   `junit`: For unit testing.
    *   `espresso`: For UI testing.

## Features

*   Displays a list of items in a shopping cart.
*   Allows users to add, remove, and dismiss items from the cart.
*   Calculates and displays the total number of items and the total price.
*   Uses a `BottomNavigationView` for app navigation.

---

# Приложение "Корзина"

Это пример простого Android-приложения, демонстрирующего функциональность корзины для покупок.

## Архитектура

Проект следует архитектурному паттерну Model-View-ViewModel (MVVM).

*   **View**: Пользовательский интерфейс построен с использованием `BinFragment`, который отвечает за отображение товаров в корзине и обработку взаимодействий с пользователем. Он наблюдает за `BinViewModel` для получения обновлений данных.
*   **ViewModel**: `BinViewModel` выступает в роли моста между View и Model. Он содержит бизнес-логику, получает данные из `BinRepository` и предоставляет их для View с помощью `LiveData`.
*   **Model**: Слой данных состоит из:
    *   `BinRepository`: интерфейс, который определяет операции с данными.
    *   `BinRepositoryImpl`: реализация `BinRepository`. Он использует два источника данных: `BinDataSourceProducts` и `BinDataSourceCache`.
    *   `BinDataSourceProducts`: получает данные о товарах из локального JSON-файла (`tovary_i_kategorii.json`).
    *   `BinDataSourceCache`: кэширует данные о товарах с помощью `SharedPreferences` для сохранения данных.

## Зависимости

В проекте используются следующие ключевые библиотеки:

*   **Библиотеки AndroidX**:
    *   `appcompat`: для обратной совместимости компонентов Material Design.
    *   `material`: для компонентов Material Design.
    *   `activity`: для управления активностями.
    *   `constraintlayout`: для создания сложных макетов.
    *   `lifecycle`: для управления жизненным циклом компонентов пользовательского интерфейса.
    *   `recyclerview`: для отображения списков элементов.
*   **Пользовательский интерфейс**:
    *   `glide`: для загрузки и кэширования изображений.
    *   `ViewBinding`: для удобного взаимодействия с представлениями.
*   **Данные**:
    *   `gson`: для сериализации и десериализации JSON.
*   **Тестирование**:
    *   `junit`: для модульного тестирования.
    *   `espresso`: для тестирования пользовательского интерфейса.

## Функциональность

*   Отображает список товаров в корзине.
*   Позволяет пользователям добавлять, удалять и убирать товары из корзины.
*   Рассчитывает и отображает общее количество товаров и общую стоимость.
*   Использует `BottomNavigationView` для навигации по приложению.
