<template>
  <swiper
      class="swiper"
      :modules="modules"
      :space-between="30"
      effect="fade"
      :navigation="true"
      :pagination="{ clickable: true }"
      :autoplay="{ delay: 3000, disableOnInteraction: false }"
  >
    <swiper-slide class="slide" v-for="index in 5" :key="index">
      <router-link to="/freeTalk">
        <img :src="`src/assets/images/swiper-img/${index}.jpg`" alt=""/>
      </router-link>
    </swiper-slide>
  </swiper>
</template>

<script lang="ts">
import {defineComponent} from 'vue'
import {Autoplay, EffectFade} from 'swiper/modules'
import {Swiper, SwiperSlide} from 'swiper/vue'
import 'swiper/css'
import 'swiper/css/pagination'
import 'swiper/css/navigation'
import 'swiper/css/effect-fade'

export default defineComponent({
  name: 'SwiperComponents',
  title: 'Fade effect',
  url: import.meta.url,
  components: {
    Swiper,
    SwiperSlide
  },
  setup() {
    return {
      modules: [Autoplay, EffectFade]
    }
  }
})
</script>

<style lang="scss" scoped>
@import '@/styles/variables';
@import '@/styles/mixins';
@import '../styles/style';

.swiper {
  @include swiper-wrapper($height: 360px);

  .slide {
    @include swiper-slide();

    img {
      display: flex;
      width: 100%;
      height: 100%;
    }
  }
}
</style>