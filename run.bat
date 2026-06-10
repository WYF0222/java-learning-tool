@echo off
chcp 65001 >nul
echo ========================================
echo   ☕ Java 小白学习乐园 - 启动中...
echo ========================================
echo.

:: 检查是否已编译
if not exist "out\javalearner\Main.class" (
    echo 📦 未找到编译文件，正在自动编译...
    call compile.bat
    if %ERRORLEVEL% NEQ 0 (
        pause
        exit /b 1
    )
)

echo 🚀 正在启动学习软件...
echo.
java -cp out javalearner.Main
pause
