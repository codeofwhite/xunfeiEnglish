<!--语音转文字-->
<template>
  <div class="container">
    <div class="greeting">你好</div>
    <VoiceTranslate></VoiceTranslate>
  </div>
</template>

<script>
import {defineComponent} from "vue";
import VoiceTranslate from "@/components/SpeechTranslate.vue";

export default defineComponent({
  components: {VoiceTranslate}
})
</script>

<style scoped>
.container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  min-height: 50vh;
}

.greeting {
  margin-bottom: 20px;
  font-size: 2em;
  color: #333;
}
</style>