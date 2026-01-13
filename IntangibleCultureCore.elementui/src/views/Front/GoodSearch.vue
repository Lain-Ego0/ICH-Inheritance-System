<template>
    <div class="search-box">
        <i class="el-icon-search search-icon"></i>
        <input type="text" v-model="_KeyWord" placeholder="请输入你喜欢的文创品" class="text" />
        <div class="search-btn" @click="SearchBtn()">搜 索</div>
    </div>
</template>

<script>
export default {
    name: "GoodSearch",

    props: {
        KeyWord: {
            type: String,
            default: ""
        }
    },
    data() {
        return {
            _KeyWord: ""
        };
    },
    created() {
        this._KeyWord = this.KeyWord;
    },
    methods: {
        SearchBtn() {
            this.$emit("Search", this._KeyWord)

        },
    }
}
</script>

<style scoped>
.search-box {
    display: flex;
    align-items: center;
    justify-content: space-around;
    background: white;
    height: 50px;
    border: 2px solid var(--primary-color);
    border-radius: 10px;
    padding: 3px 5px;
}

.search-box .search-select {
    padding: 10px;
    border: none;
}



.search-box .search-icon {
    font-weight: bold;
    font-size: 20px;
    margin-left: 5px;
    color: rgba(45, 45, 46, 0.404);
}

.search-box .text {
    padding-left: 10px;
    padding-right: 20px;
    color: #11192d;
}

.search-box .search-option {
    padding: 5px 2px;
}


.search-box .text {
    flex: 1;
}

.search-box .search-btn {
    width: 100px;
    text-align: center;
    color: white;
    height: 40px;
    line-height: 40px;
    border-radius: 7px;
    border: 2px solid var(--primary-color);
    background-color: var(--primary-color);
    cursor: pointer;
}
</style>