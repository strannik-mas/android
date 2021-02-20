package com.example.strannik.currencyrates;

/**
 * Параметризованный результат чего-либо
 * Если все ОК возвращаем результат, если нет,
 * то exception
 *
 * Можно разнести процесс получения результата и результат
 * Тот, кому нужен результат, получит или его или 
 * исключение
 */
public class Result<T> {
    public T result;
    public String title;
    public Exception exception;
}
