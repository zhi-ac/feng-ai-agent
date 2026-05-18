<template>
  <div class="chat-page">
    <div class="chat-header">
      <button class="back-btn" @click="$router.push('/')">← 返回</button>
      <h2>🧠 AI 超级智能体</h2>
      <span class="chat-id">Manus Agent</span>
    </div>

    <div class="chat-messages" ref="messagesRef">
      <div
        v-for="(msg, index) in messages"
        :key="index"
        :class="['message', msg.role === 'user' ? 'user' : 'assistant']"
      >
        <div class="avatar">{{ msg.role === 'user' ? '👤' : '🧠' }}</div>
        <div class="bubble">
          <span v-if="msg.step" class="step-label">Step {{ msg.step }}</span>
          <span v-html="renderContent(msg.content)"></span>
          <span v-if="msg.loading" class="cursor">▌</span>
        </div>
      </div>
    </div>

    <div class="chat-input">
      <input
        v-model="inputText"
        placeholder="输入你的任务或问题..."
        @keyup.enter="sendMessage"
        :disabled="loading"
      />
      <button @click="sendMessage" :disabled="loading || !inputText.trim()">
        {{ loading ? '思考中...' : '发送' }}
      </button>
    </div>
  </div>
</template>

<script setup>
import { ref, nextTick, onUnmounted } from 'vue'

const messages = ref([])
const inputText = ref('')
const loading = ref(false)
const messagesRef = ref(null)
let eventSource = null
let currentStepIndex = -1 // 当前正在流式输出的气泡索引
let stepCount = 0

onUnmounted(() => {
  if (eventSource) {
    eventSource.close()
    eventSource = null
  }
})

function scrollToBottom() {
  nextTick(() => {
    if (messagesRef.value) {
      messagesRef.value.scrollTop = messagesRef.value.scrollHeight
    }
  })
}

function renderContent(content) {
  return content.replace(/\n/g, '<br/>')
}

async function sendMessage() {
  const text = inputText.value.trim()
  if (!text || loading.value) return

  messages.value.push({ role: 'user', content: text })
  inputText.value = ''
  loading.value = true
  stepCount = 0
  currentStepIndex = -1

  const params = new URLSearchParams({ message: text })

  eventSource = new EventSource(`/api/ai/manus/chat?${params.toString()}`)

  eventSource.onmessage = (event) => {
    const data = event.data
    if (!data) return

    // 上一个 step 结束，关闭其 loading 状态
    if (currentStepIndex >= 0) {
      messages.value[currentStepIndex].loading = false
    }

    // 每个 SSE 事件作为一个新的 step 气泡
    stepCount++
    messages.value.push({ role: 'assistant', content: data, loading: true, step: stepCount })
    currentStepIndex = messages.value.length - 1
    scrollToBottom()
  }

  eventSource.onerror = () => {
    // 关闭最后一个 step 的 loading
    if (currentStepIndex >= 0) {
      messages.value[currentStepIndex].loading = false
    }
    loading.value = false
    eventSource.close()
    eventSource = null
    scrollToBottom()
  }
}
</script>

<style scoped>
.chat-page {
  display: flex;
  flex-direction: column;
  height: 100vh;
  background: linear-gradient(180deg, #1a1a2e, #16213e);
}

.chat-header {
  display: flex;
  align-items: center;
  gap: 1rem;
  padding: 0.75rem 1.5rem;
  background: rgba(108, 92, 231, 0.15);
  border-bottom: 1px solid rgba(108, 92, 231, 0.2);
  backdrop-filter: blur(10px);
}

.back-btn {
  background: none;
  border: 1px solid rgba(255, 255, 255, 0.3);
  color: rgba(255, 255, 255, 0.8);
  padding: 0.4rem 0.8rem;
  border-radius: 8px;
  cursor: pointer;
  font-size: 0.85rem;
  transition: all 0.2s;
}

.back-btn:hover {
  background: rgba(255, 255, 255, 0.1);
  color: #fff;
}

.chat-header h2 {
  color: #fff;
  font-size: 1.1rem;
  font-weight: 600;
  flex: 1;
}

.chat-id {
  color: rgba(255, 255, 255, 0.4);
  font-size: 0.75rem;
}

.chat-messages {
  flex: 1;
  overflow-y: auto;
  padding: 1.5rem;
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.message {
  display: flex;
  gap: 0.75rem;
  max-width: 80%;
  animation: fadeIn 0.3s ease;
}

.message.user {
  align-self: flex-end;
  flex-direction: row-reverse;
}

.message.assistant {
  align-self: flex-start;
}

.avatar {
  width: 36px;
  height: 36px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 1.1rem;
  flex-shrink: 0;
  background: rgba(255, 255, 255, 0.1);
}

.bubble {
  padding: 0.75rem 1rem;
  border-radius: 16px;
  line-height: 1.6;
  font-size: 0.95rem;
  word-break: break-word;
}

.message.user .bubble {
  background: linear-gradient(135deg, #6c5ce7, #a29bfe);
  color: #fff;
  border-bottom-right-radius: 4px;
}

.message.assistant .bubble {
  background: rgba(255, 255, 255, 0.1);
  color: rgba(255, 255, 255, 0.9);
  border-bottom-left-radius: 4px;
}

.step-label {
  display: inline-block;
  background: rgba(108, 92, 231, 0.4);
  color: #fff;
  font-size: 0.75rem;
  font-weight: 600;
  padding: 0.15rem 0.5rem;
  border-radius: 4px;
  margin-right: 0.5rem;
  vertical-align: middle;
}

.cursor {
  animation: blink 1s step-end infinite;
  color: #a29bfe;
}

@keyframes fadeIn {
  from { opacity: 0; transform: translateY(8px); }
  to { opacity: 1; transform: translateY(0); }
}

@keyframes blink {
  50% { opacity: 0; }
}

.chat-input {
  display: flex;
  gap: 0.75rem;
  padding: 1rem 1.5rem;
  background: rgba(0, 0, 0, 0.3);
  border-top: 1px solid rgba(255, 255, 255, 0.1);
}

.chat-input input {
  flex: 1;
  padding: 0.75rem 1rem;
  border-radius: 12px;
  border: 1px solid rgba(255, 255, 255, 0.15);
  background: rgba(255, 255, 255, 0.08);
  color: #fff;
  font-size: 0.95rem;
  outline: none;
  transition: border-color 0.2s;
}

.chat-input input::placeholder {
  color: rgba(255, 255, 255, 0.35);
}

.chat-input input:focus {
  border-color: rgba(108, 92, 231, 0.5);
}

.chat-input button {
  padding: 0.75rem 1.5rem;
  border-radius: 12px;
  border: none;
  background: linear-gradient(135deg, #6c5ce7, #a29bfe);
  color: #fff;
  font-size: 0.95rem;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.2s;
}

.chat-input button:hover:not(:disabled) {
  transform: scale(1.05);
  box-shadow: 0 4px 16px rgba(108, 92, 231, 0.4);
}

.chat-input button:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}
</style>
