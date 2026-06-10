@echo off
chcp 65001 >nul
echo ========================================
echo   ☕ Java 小白学习乐园 - 编译脚本
echo ========================================
echo.

:: 设置源文件和输出目录
set SRC_DIR=src
set OUT_DIR=out
set MAIN_CLASS=javalearner.Main

:: 清理旧的编译文件
if exist "%OUT_DIR%" (
    echo [清理] 删除旧的编译文件...
    rmdir /s /q "%OUT_DIR%"
)

:: 创建输出目录
mkdir "%OUT_DIR%"

:: 收集所有Java源文件
echo [编译] 正在编译Java源文件...
dir /s /b "%SRC_DIR%\*.java" > sources.txt

:: 编译
javac -encoding UTF-8 -d "%OUT_DIR%" @sources.txt
if %ERRORLEVEL% NEQ 0 (
    echo ❌ 编译失败！请检查错误信息。
    del sources.txt
    pause
    exit /b 1
)

del sources.txt
echo ✅ 编译成功！
echo.
echo 运行方式：java -cp out javalearner.Main
echo 或双击 run.bat
pause
