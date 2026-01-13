<template>
    <div class="main-container">
        <div class="Topstory-mainColumn ">

            <div class=" flex align-center margin-top-xs">
                <el-input placeholder="请输入你的关键字" prefix-icon="el-icon-search" v-model="KeyWord">
                </el-input>
                <el-button class="margin-left-xs" mini type="primary" icon="el-icon-search" @click="SearchTopic()">搜
                    索</el-button>
            </div>
            <div class=" margin-top-xs">
                <div class="tab-container">
                    <div class="scroll-tabs">
                        <div class="tab-item" :class="{ active: active == -1 }" @click="tabChange(-1)">推荐</div>
                        <div class="tab-item" :class="{ active: active == -2 }" @click="tabChange(-2)">近期</div>
                        <div class="tab-item" :class="{ active: active == -3 }" @click="tabChange(-3)">热门</div>
                        <div class="tab-item" :class="{ active: active == -4 }" @click="tabChange(-4)">优选</div>
                        <div v-for="(item, index) in TopicTypeList" :key="item.Id" class="tab-item"
                            :class="{ active: active == item.Id }" @click="tabChange(item.Id)">
                            {{ item.Name }}
                        </div>
                    </div>
                </div>
            </div>

            <template v-if="active == -1">
                <PaginationBox ref="RecommendPaginationBox" :where="where" url="/Topic/RecommendTop10">
                    <template v-slot:content="{ data }">
                        <TopicListCard :DataList="data"></TopicListCard>
                    </template>
                </PaginationBox>
            </template>
            <template v-else>
                <PaginationBox ref="PaginationBox" :where="where" url="/Topic/List">
                    <template v-slot:content="{ data }">
                        <TopicListCard :DataList="data"></TopicListCard>
                    </template>
                </PaginationBox>
            </template>

        </div>




    </div>
</template>

<script>

import PaginationBox from "@/components/Pagination/PaginationBox.vue";
import TopicListCard from "@/views/Front/TopicListCard.vue";

import { mapGetters } from 'vuex';

export default {
    components: {},
    name: 'TopicList',
    computed: {
        ...mapGetters([
            'Token', 'UserInfo', 'RoleType', 'HasUserInfo', 'ColumnType', "UserId"
        ])
    },
    components: {

        PaginationBox,
        TopicListCard
    },
    data() {
        return {
            active: -1,
            where: {
                AuditStatus: 2,
            },
            KeyWord: '',
            TopicList: [],
            TopicTypeList: [],


        };
    },
    mounted() {

        this.TopicTypeListApi();
    },
    methods: {

        async TopicListApi(where) {
            let { Data: { Items } } = await this.$Post(`/Topic/List`, { ...this.where, ...where });
            this.TopicList = Items;
        },
        async GetMyFirendTopicListApi() {
            let { Data: { Items } } = await this.$Post(`/Topic/GetMyFirendTopicList`, this.where);
            this.TopicList = Items;
        },
        async RecommendTop10Api(where) {
            let { Data: { Items } } = await this.$Post(`/Topic/RecommendTop10`, {});
            this.TopicList = Items;
        },
        async TopicTypeListApi(where) {
            let { Data: { Items } } = await this.$Post(`/TopicType/List`, {});
            this.TopicTypeList = Items;
        },
        async tabChange(e) {
            this.active = e;
            this.$nextTick(() => {
                if (this.active == 0) {
                    this.GetMyFirendTopicListApi();
                }
                else if (this.active == -1) {

                    this.$refs.RecommendPaginationBox.Reload({ AuditStatus: 2 })
                }
                else if (this.active == -2) {
                    setTimeout(() => {
                        this.$refs.PaginationBox.Reload({ AuditStatus: 2, IsNew: true })
                    }, 10)

                }
                else if (this.active == -3) {
                    setTimeout(() => {
                        this.$refs.PaginationBox.Reload({ AuditStatus: 2, IsHot: true })
                    }, 10)
                }
                else if (this.active == -4) {
                    setTimeout(() => {
                        this.$refs.PaginationBox.Reload({ AuditStatus: 2, IsRecommand: true })
                    }, 10)
                }
                else {
                    setTimeout(() => {
                        this.$refs.PaginationBox.Reload({ AuditStatus: 2, TopicTypeId: this.active })
                    }, 10)
                }
            })

        },
        SearchTopic() {
            this.$router.push({
                path: `/Front/TopicSearch`, query: {
                    KeyWord: this.KeyWord,
                }
            });
        },

    }


}
</script>

<style lang="scss" scoped>
.tab-container {
    background: #fff;
    border-radius: 8px;
    padding: 8px;
    margin: 8px 0;
    box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}

.scroll-tabs {
    display: flex;
    overflow-x: auto;
    scrollbar-width: none; // Firefox
    -ms-overflow-style: none; // IE and Edge

    &::-webkit-scrollbar {
        display: none; // Chrome, Safari, Opera
    }

    gap: 12px;
    padding: 4px;
}

.tab-item {
    padding: 8px 16px;
    white-space: nowrap;
    cursor: pointer;
    border-radius: 20px;
    transition: all 0.3s ease;
    font-size: 14px;

    &:hover {
        background: #f5f5f5;
    }

    &.active {
        background: var(--primary-color);
        color: white;
    }
}
</style>
