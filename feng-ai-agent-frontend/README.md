# Feng AI Agent Frontend

基于 Vue3 + Vite 的 AI 智能体前端应用。

## 功能

- **主页**：切换不同 AI 应用
- **AI 恋爱大师**：SSE 流式聊天，自动生成会话 ID
- **AI 超级智能体**：SSE 流式聊天，按 Step 逐步输出

## 技术栈

- Vue 3
- Vue Router 4
- Axios
- Vite 6

## 开发

```bash
# 安装依赖
npm install

# 启动开发服务器
npm run dev

# 构建生产版本
npm run build

# 预览生产构建
npm run preview
```

## 后端接口

前端通过 Vite 代理转发 `/api` 请求到 `http://localhost:8123`。

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/ai/love_app/chat/sse` | GET (SSE) | 恋爱大师流式对话，参数：message, chatId |
| `/api/ai/manus/chat` | GET (SSE) | 超级智能体流式对话，参数：message |
