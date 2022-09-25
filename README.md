# Cloudphoto

## Техническое требование к команде

[Задание 1. Разработать приложение командной строки "Фотоархив"](https://docs.itiscl.ru/2022-2023/vvot/task01.html)

## Запуск программы

1. Для того, чтобы запустить программу, необходимо иметь на компьютере JAVA 17 версии или выше.
   Можно скачать через терминал с помощью команды `apt install openjdk-17-jre-headless`.
2.
    1. Чтобы скомпилировать проект, необходимо запустить maven сборку (mvn clean package).
        * [скачать maven](https://maven.apache.org/download.cgi)
        * [инструкция по установке maven](https://maven.apache.org/install.html)
    2. Либо воспользоваться
       уже [собранным архивом](https://drive.google.com/file/d/14NPZ9qiZnpvDv2HHoKgj_UDQQOmFPpuO/view?usp=sharing)
4. Для того, чтобы запустить программу, необходимо запустить скрипт `cloudphoto` (!!!Важно, чтобы
   скрипт находился в одной папке с `cloudphoto-jar-with-dependencies.jar` (в случае, если jar-архив
   был перекомпилирован, необходимо переместить архив из папки `target` в одну директорию со
   скриптом `cloudphoto`)).

## Пример вызова программы:

`./cloudphoto init`