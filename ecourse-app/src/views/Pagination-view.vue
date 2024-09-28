<template>
    <div>
        <button 
            type="button" 
            class="text-white bg-gradient-to-r from-cyan-500 
            to-blue-500 hover:bg-gradient-to-bl focus:ring-4 focus:outline-none
            focus:ring-cyan-300 dark:focus:ring-cyan-800 font-medium rounded-lg
            text-sm px-5 py-2.5 text-center me-2 mb-2"
            @click="changePage(currentPage - 1)" 
            :disabled="currentPage === 0"
            >
            Previous
        </button>
        <span>Page {{ currentPage+1 }} of {{ totalPages }}</span>
        <button 
        type="button" 
            class="text-white bg-gradient-to-r from-cyan-500 
            to-blue-500 hover:bg-gradient-to-bl focus:ring-4 focus:outline-none
            focus:ring-cyan-300 dark:focus:ring-cyan-800 font-medium rounded-lg
            text-sm px-5 py-2.5 text-center me-2 mb-2 ml-3"
            @click="changePage(currentPage + 1)" 
            :disabled="currentPage === totalPages - 1"
            >
            Next
        </button>
    </div>
</template>

<script>
export default {
    props: {
        currentPage: {
            type: Number,
            required: true
        },
        totalPages: {
            type: Number,
            required: true
        },
        itemsPerPage: {
            type: Number,
            required: true
        },
    }, 
   
    methods: {
        changePage(page) {
            if(page >= 0 && page < this.totalPages) {
                this.$emit('page-changed', page);
            }
        }
    }
}
</script>
