<template>
    <div class="page-container ">
        <div class="margin-top-xs card">
            <el-page-header @back="goBack()" content="文创品搜索">
            </el-page-header>

        </div>
        <div class="margin-top-xs">
            <GoodSearch :KeyWord="KeyWord" @Search="SearchCallBack"></GoodSearch>
        </div>
        <div class="margin-top-xs card">
            <div class="tab">
                <h3>搜索的结果如下</h3>

                <Pagination url="/Good/List" ref="Pagination" :where="{ KeyWord: KeyWord }">
                    <template v-slot:content="{ data }">
                        <GoodListCard :DataList="data"></GoodListCard>
                    </template>
                </Pagination>
            </div>


        </div>

        <div class="card margin-top-xs">
            <div class="header-title">猜你喜欢</div>
            <Pagination url="/Good/GetRecommendList" :where="{}">
                <template v-slot:content="{ data }">
                    <GoodListCard :DataList="data"></GoodListCard>
                </template>
            </Pagination>
        </div>
    </div>
</template>

<script>
import Pagination from "@/components/Pagination/PaginationBox.vue";
import GoodListCard from "./GoodListCard.vue";
import GoodSearch from "./GoodSearch.vue";

export default {
    name: "Home",
    components: {
        Pagination,
        GoodSearch,
        GoodListCard
    },
    data() {
        return {
            KeyWord: "",

        }
    },
    created() {
        this.KeyWord = this.$route.query.KeyWord;
    },
    methods: {
        //返回上一个页面
        goBack() {
            this.$router.go(-1)
        },
        SearchCallBack(e) {
            this.$refs.Pagination.Reload({ KeyWord: e });
        },

    }

}
</script>

<style scope></style>