<template>
  <div class="home">
    <div class="home-header">
      <h1>🤖 Feng AI Agent</h1>
      <p>选择一个 AI 应用开始对话</p>
    </div>
    <div class="app-cards">
      <div class="app-card love" @click="$router.push('/love')">
        <div class="card-icon">💕</div>
        <h2>AI 恋爱大师</h2>
        <p>情感咨询与恋爱指导，让你的爱情更甜蜜</p>
      </div>
      <div class="app-card manus" @click="$router.push('/manus')">
        <div class="card-icon">🧠</div>
        <h2>AI 超级智能体</h2>
        <p>全能 AI 助手，解决各类复杂任务</p>
      </div>
    </div>
  </div>
</template>

<style scoped>
.home {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 2rem;
  background: linear-gradient(135deg, #0f0c29, #302b63, #24243e);
}

.home-header {
  text-align: center;
  margin-bottom: 3rem;
}

.home-header h1 {
  font-size: 2.5rem;
  color: #fff;
  margin-bottom: 0.5rem;
  font-weight: 700;
}

.home-header p {
  font-size: 1.1rem;
  color: rgba(255, 255, 255, 0.6);
}

.app-cards {
  display: flex;
  gap: 2rem;
  flex-wrap: wrap;
  justify-content: center;
}

.app-card {
  width: 280px;
  padding: 2.5rem 2rem;
  border-radius: 20px;
  cursor: pointer;
  text-align: center;
  transition: all 0.3s ease;
  color: #fff;
}

.app-card.love {
  background: linear-gradient(135deg, #ff6b6b, #ee5a24);
  box-shadow: 0 8px 32px rgba(255, 107, 107, 0.3);
}

.app-card.manus {
  background: linear-gradient(135deg, #6c5ce7, #a29bfe);
  box-shadow: 0 8px 32px rgba(108, 92, 231, 0.3);
}

.app-card:hover {
  transform: translateY(-8px) scale(1.03);
}

.app-card.love:hover {
  box-shadow: 0 16px 48px rgba(255, 107, 107, 0.5);
}

.app-card.manus:hover {
  box-shadow: 0 16px 48px rgba(108, 92, 231, 0.5);
}

.card-icon {
  font-size: 3.5rem;
  margin-bottom: 1rem;
}

.app-card h2 {
  font-size: 1.4rem;
  margin-bottom: 0.75rem;
  font-weight: 600;
}

.app-card p {
  font-size: 0.95rem;
  color: rgba(255, 255, 255, 0.85);
  line-height: 1.5;
}
</style>
