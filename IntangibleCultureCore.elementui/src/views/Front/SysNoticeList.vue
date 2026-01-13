<template>
    <div>
        <PaginationBox ref="PaginationBox" :where="where" url="/SysNotice/List">
            <template v-slot:content="{ data }">
                <div class="notice-list">
                    <el-card v-for="item in data" :key="item.Id" class="notice-item">
                        <div class="notice-header" @click="ToDetail(item.Id)">
                            <h3 class="notice-title">{{ item.Title }}</h3>
                            <span class="notice-time">{{ item.CreationTime }}</span>
                        </div>
                        <div class="notice-content" @click="ToDetail(item.Id)">
                            {{ item.Content.length > 100 ? item.Content.substring(0, 100) + '...' : item.Content }}
                        </div>
                    </el-card>
                </div>
            </template>
        </PaginationBox>
    </div>
</template>
<script>
import PaginationBox from "@/components/Pagination/PaginationBox.vue";
import SelectTab from '@/components/Select/SelectTab.vue';
import { mapGetters } from 'vuex';
export default {
    name: 'SysNotice',
    computed: {
        ...mapGetters([
            'Token', 'UserInfo', 'RoleType', 'HasUserInfo', 'ColumnType', 'UserId'])
    },
    components: {
        PaginationBox,
        SelectTab
    },
    data() {
        return {
            where: {}
        }
    },
    created() { },
    methods: {
        ToDetail(Id) {
            this.$router.push({
                path: '/Front/SysNoticeDetail',
                query: {
                    SysNoticeId: Id,
                }
            })
        }
    },
}
</script>
<style scoped>
.notice-list {
    width: 100%;
}

.notice-item {
    margin-bottom: 15px;
    cursor: pointer;
    transition: all 0.3s;
}

.notice-item:hover {
    transform: translateY(-2px);
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.notice-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 10px;
}

.notice-title {
    margin: 0;
    font-size: 16px;
    color: #303133;
}

.notice-time {
    font-size: 14px;
    color: #909399;
}

.notice-content {
    color: #606266;
    font-size: 14px;
    line-height: 1.6;
}
</style>