<template>
    <div v-if="Inheritor">
        <div class="detail-container margin-top-xs">
            <!-- 头部信息区域 -->
            <div class="header-section">
                <div class="avatar-container">
                    <img :src="Inheritor.ImageUrls" alt="传承人照片" class="avatar-image">
                    <div class="id-badge">ID: {{ Inheritor.No }}</div>
                </div>
                <div class="basic-info">
                    <h1 class="name">{{ Inheritor.Name }}</h1>
                    <div class="info-tags">
                        <span class="tag"><i class="el-icon-user"></i> {{ Inheritor.Sex }}</span>
                        <span class="tag"><i class="el-icon-location"></i> {{ formatLocation(Inheritor.ProviceCityArea)
                        }}</span>
                        <span class="tag"><i class="el-icon-date"></i> {{ formatDate(Inheritor.BirthDate) }}</span>
                        <span class="tag"><i class="el-icon-collection-tag"></i> {{ Inheritor.Nation }}</span>
                    </div>
                </div>
            </div>

            <!-- 内容区域 -->
            <div class="content-section">
                <div class="section-title">
                    <div class="title-line"></div>
                    <h2>传承人介绍</h2>
                    <div class="title-line"></div>
                </div>
                <div class="content-text" v-html="Inheritor.Content"></div>
            </div>
        </div>
    </div>
</template>
<script>
import PaginationBox from "@/components/Pagination/PaginationBox.vue";
import SelectTab from '@/components/Select/SelectTab.vue';
import { mapGetters } from 'vuex';
export default {
    name: 'Inheritor',
    computed: {
        ...mapGetters([
            'Token', 'UserInfo', 'RoleType', 'HasUserInfo', 'ColumnType', 'UserId'
        ]),
    },
    components: {
        PaginationBox,
        SelectTab
    },
    data() {
        return {
            Inheritor: null,
        }
    },
    watch: {
        "$route": async function (to, from) {
            await this.GetInheritorApi();
        }
    },
    created() {
        this.GetInheritorApi();
    },
    methods: {
        //得到数据接口
        async GetInheritorApi() {
            let { Data } = await this.$Post("/Inheritor/Get",
                {
                    Id: this.$route.query.InheritorId
                })
            this.Inheritor = Data;
            this.$nextTick(() => { })
        },
        // 格式化地址
        formatLocation(location) {
            if (!location) return '';
            return location.split(',').join(' · ');
        },
        // 格式化日期
        formatDate(dateStr) {
            if (!dateStr) return '';
            return dateStr.split(' ')[0];
        }
    },
}
</script>
<style scoped lang="scss">
/* 整体容器样式 */
.detail-container {
    background-color: #fff;
    border-radius: 8px;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
    padding: 30px;
    margin-top: 20px;
}

/* 头部区域样式 */
.header-section {
    display: flex;
    margin-bottom: 40px;
    position: relative;
    background: linear-gradient(135deg, #f5f7fa 0%, #e4e8f0 100%);
    border-radius: 12px;
    padding: 30px;
}

/* 头像容器样式 */
.avatar-container {
    position: relative;
    margin-right: 40px;
}

/* 头像图片样式 */
.avatar-image {
    width: 180px;
    height: 180px;
    object-fit: cover;
    border-radius: 10px;
    box-shadow: 0 8px 16px rgba(0, 0, 0, 0.15);
    border: 4px solid #fff;
}

/* ID徽章样式 */
.id-badge {
    width: 140px;
    position: absolute;
    bottom: -10px;
    left: 50%;
    transform: translateX(-50%);
    background-color: #D85A4E;
    color: white;
    padding: 5px 15px;
    border-radius: 20px;
    text-align: center;
    font-size: 14px;
    font-weight: bold;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}

/* 基本信息样式 */
.basic-info {
    display: flex;
    flex-direction: column;
    justify-content: center;
}

/* 姓名样式 */
.name {
    font-size: 32px;
    margin-bottom: 20px;
    color: #333;
    position: relative;
    display: inline-block;
}

.name:after {
    content: '';
    position: absolute;
    bottom: -8px;
    left: 0;
    width: 60px;
    height: 4px;
    background: #D85A4E;
    border-radius: 2px;
}

/* 信息标签样式 */
.info-tags {
    display: flex;
    flex-wrap: wrap;
    gap: 15px;
}

.tag {
    background-color: #f0f2f5;
    padding: 8px 15px;
    border-radius: 20px;
    font-size: 14px;
    color: #555;
    display: flex;
    align-items: center;
    gap: 5px;
}

.tag i {
    color: #D85A4E;
}

/* 内容区域样式 */
.content-section {
    margin-bottom: 40px;
    background-color: #f9fafc;
    padding: 30px;
    border-radius: 12px;
    position: relative;
}

/* 标题样式 */
.section-title {
    display: flex;
    align-items: center;
    margin-bottom: 25px;
}

.section-title h2 {
    font-size: 22px;
    color: #333;
    margin: 0 15px;
}

.title-line {
    flex: 1;
    height: 1px;
    background: linear-gradient(90deg, transparent, #D85A4E, transparent);
}

/* 内容文本样式 */
.content-text {
    font-size: 16px;
    line-height: 1.8;
    color: #444;
}

/* 响应式调整 */
@media (max-width: 768px) {
    .header-section {
        flex-direction: column;
        align-items: center;
        text-align: center;
    }

    .avatar-container {
        margin-right: 0;
        margin-bottom: 30px;
    }

    .name:after {
        left: 50%;
        transform: translateX(-50%);
    }

    .info-tags {
        justify-content: center;
    }
}
</style>