@echo off
chcp 65001 > nul
echo ============================================
echo    个人博客系统 - 一键启动脚本
echo ============================================
echo.

:: 检查Java
java -version > nul 2>&1
if %errorlevel% neq 0 (
    echo [错误] 未检测到Java环境，请先安装JDK 17+
    pause
    exit /b 1
)

:: 检查Node
node -v > nul 2>&1
if %errorlevel% neq 0 (
    echo [错误] 未检测到Node.js环境，请先安装Node.js 18+
    pause
    exit /b 1
)

:: 检查Maven
mvn -v > nul 2>&1
if %errorlevel% neq 0 (
    echo [错误] 未检测到Maven环境，请先安装Maven
    pause
    exit /b 1
)

echo [1/4] 检查MySQL数据库...
echo 请确保MySQL已启动并创建了blog数据库
echo.
echo [2/4] 正在构建后端...
cd /d "%~dp0blog-backend"
call mvn clean package -DskipTests
if %errorlevel% neq 0 (
    echo [错误] 后端构建失败
    pause
    exit /b 1
)

echo.
echo [3/4] 正在启动后端服务...
start "BlogBackend" cmd /c "java -jar target\blog-backend-1.0.0.jar"

echo.
echo [4/4] 正在启动前端服务...
cd /d "%~dp0blog-frontend"
call npm install
start "BlogFrontend" cmd /c "npm run dev"

echo.
echo ============================================
echo    启动完成！
echo    后端地址: http://localhost:8080
echo    前端地址: http://localhost:3000
echo    API文档: http://localhost:8080/doc.html
echo ============================================
echo.
echo 按任意键退出此窗口（服务将继续在后台运行）...
pause > nul
