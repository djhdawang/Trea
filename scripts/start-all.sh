# 个人博客系统启动脚本 (GitBash/Linux/macOS)

echo "============================================"
echo "   个人博客系统 - 启动脚本"
echo "============================================"

# 检查Java
if ! command -v java &> /dev/null; then
    echo "[错误] 未检测到Java环境，请先安装JDK 17+"
    exit 1
fi

# 检查Node
if ! command -v node &> /dev/null; then
    echo "[错误] 未检测到Node.js环境，请先安装Node.js 18+"
    exit 1
fi

# 检查Maven
if ! command -v mvn &> /dev/null; then
    echo "[错误] 未检测到Maven环境，请先安装Maven"
    exit 1
fi

echo "[1/4] 检查MySQL数据库..."
echo "请确保MySQL已启动并创建了blog数据库"
echo ""

echo "[2/4] 正在构建后端..."
cd blog-backend
mvn clean package -DskipTests
if [ $? -ne 0 ]; then
    echo "[错误] 后端构建失败"
    exit 1
fi

echo ""
echo "[3/4] 正在启动后端服务..."
nohup java -jar target/blog-backend-1.0.0.jar > backend.log 2>&1 &
echo "后端已启动 (PID: $!)"

echo ""
echo "[4/4] 正在启动前端服务..."
cd ../blog-frontend
npm install
npm run dev > frontend.log 2>&1 &
echo "前端已启动"

echo ""
echo "============================================"
echo "   启动完成！"
echo "   后端地址: http://localhost:8080"
echo "   前端地址: http://localhost:3000"
echo "   API文档: http://localhost:8080/doc.html"
echo "============================================"
