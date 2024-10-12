<template>
  <nav aria-label="Page navigation example">
    <ul class="flex items-center -space-x-px h-10 text-base">
      <li v-if="currentPage > 0">
        <a href="#" @click="goToPage(currentPage - 1)" class="flex items-center justify-center px-4 h-10 leading-tight text-gray-500 bg-white border border-e-0 border-gray-300 rounded-s-lg hover:bg-gray-100 hover:text-gray-700">
          <span class="sr-only">Previous</span>
          <svg class="w-3 h-3 rtl:rotate-180" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 6 10">
            <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 1 1 5l4 4"/>
          </svg>
        </a>
      </li>

      <li v-for="(page, index) in displayedPages" :key="index">
        <a
          v-if="typeof page === 'number'"
          href="#"
          @click="goToPage(page - 1)"
          class="flex items-center justify-center px-4 h-10 leading-tight"
          :class="{
            'text-blue-600 font-bold border border-blue-300 bg-blue-50': page - 1 === currentPage, 
            'text-gray-500 bg-white border border-gray-300 hover:bg-gray-100 hover:text-gray-700': page - 1 !== currentPage
          }"
        >
          {{ page }}
        </a>

        <span v-else class="flex items-center justify-center px-4 h-10">...</span>
      </li>

      <li v-if="currentPage < totalPages - 1">
        <a href="#" @click="goToPage(currentPage + 1)" class="flex items-center justify-center px-4 h-10 leading-tight text-gray-500 bg-white border border-gray-300 rounded-e-lg hover:bg-gray-100 hover:text-gray-700">
          <span class="sr-only">Next</span>
          <svg class="w-3 h-3 rtl:rotate-180" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 6 10">
            <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="m1 9 4-4-4-4"/>
          </svg>
        </a>
      </li>
    </ul>
  </nav>
</template>

<script>
export default {
  props: {
    totalPages: {
      type: Number,
      required: true,
    },
    currentPage: {
      type: Number,
      required: true,
    },
  },
  computed: {
    displayedPages() {
      const pages = [];

      // First three pages
      for (let i = 1; i <= 3; i++) {
        if (i <= this.totalPages) pages.push(i);
      }

      // Ellipsis if total pages are greater than 6
      if (this.totalPages > 6) {
        pages.push('...');
      }

      // Last two pages
      for (let i = Math.max(this.totalPages - 1, 4); i <= this.totalPages; i++) {
        if (i > 3) pages.push(i);
      }

      return pages;
    },
  },
  methods: {
    goToPage(page) {
      this.$emit('update:currentPage', page);
    },
  },
};
</script>

<style scoped>
/* Thêm các kiểu CSS tùy chỉnh nếu cần */
</style>
