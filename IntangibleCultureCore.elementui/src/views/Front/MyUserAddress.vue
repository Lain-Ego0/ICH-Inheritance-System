<template>
    <div class="page-container ">
        <div class="card margin-top-xs margin-bottom-xs">
            <el-page-header @back="goBack()" content="我的收货地址">
            </el-page-header>
        </div>


        <el-dialog :title="formData.Id ? '修改收货地址' : '添加收货地址'" :visible.sync="editorShow" width="50%" :lock-scroll="true"
            height="800px">
            <el-form v-if="editorShow == true" ref="editModalForm" :rules="editModalFormRules" :model="formData"
                label-width="140px" size="mini">
                <el-row :gutter="10" class="EditFromBody">
                    <el-col :span="24">
                        <el-form-item label="收货姓名" prop="Name">
                            <el-input type="text" v-model.trim="formData.Name" placeholder="请输入收货姓名"
                                :clearable="true"></el-input>
                        </el-form-item>
                    </el-col>

                    <el-col :span="24">
                        <el-form-item label="省市区" prop="ProviceCityArea">
                            <AddressSelect v-model="formData.ProviceCityArea">
                            </AddressSelect>
                        </el-form-item>
                    </el-col>

                    <el-col :span="24">
                        <el-form-item label="电话" prop="Phone">
                            <el-input type="text" v-model.trim="formData.Phone" placeholder="请输入电话"
                                :clearable="true"></el-input>
                        </el-form-item>
                    </el-col>

                    <el-col :span="24">
                        <el-form-item label="性别" prop="Sex">
                            <el-radio-group v-model="formData.Sex">
                                <el-radio label="男">男</el-radio>
                                <el-radio label="女">女</el-radio>
                                <el-radio label="保密">保密</el-radio>
                            </el-radio-group>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="门牌号" prop="HouseNumber">
                            <el-input type="text" v-model.trim="formData.HouseNumber" placeholder="请输入门牌号"
                                :clearable="true"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="详细地址" prop="Address">
                            <el-input type="textarea" v-model.trim="formData.Address" placeholder="请输入地址"
                                :clearable="true"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>


                <el-row type="flex" justify="end" align="bottom">
                    <el-form-item>
                        <el-button type="primary" plain @click="CreateOrEditForm()">确 定</el-button>
                        <el-button @click="editorShow = false">取 消</el-button>
                    </el-form-item>
                </el-row>
            </el-form>

        </el-dialog>

        <div class="card marign-top-xs">
            <div class="flex justify-between">
                <el-button type="primary" size="mini" plain icon="el-icon-edit" @click="ShowEditModal()">新 增 地 址 信
                    息</el-button>
            </div>
            <Pagination url="/UserAddress/List" ref="PaginationTableId" :where="{ BelongUserId: UserId }">
                <template v-slot:content="{ data }">
                    <div class="address-box">
                        <div class="address-list">
                            <div class="address-item" v-for="(item, index) in data" :key="index">
                                <div class="address-content" @click="ShowEditModal(item.Id)">
                                    <div class="address-name">
                                        {{ item.Name }}
                                    </div>
                                    <div>{{ item.ProviceCityArea }},{{ item.Address }}</div>
                                    <div>门牌号:{{ item.HouseNumber }}</div>
                                    <div class="address-phone">
                                        <span>电话:{{ item.Phone }}</span><span>门牌号:{{ item.HouseNumber }}</span>
                                    </div>
                                    <div>
                                        性别:{{ item.Sex }}
                                    </div>
                                </div>
                                <div class="delete-btn" @click.stop="ShowDeleteModal(item.Id)">
                                    <i class="el-icon-delete"></i>
                                </div>
                            </div>
                        </div>
                    </div>
                </template>

            </Pagination>
        </div>



    </div>
</template>

<script>
import Pagination from "@/components/Pagination/PaginationBox.vue";
import AddressSelect from '@/components/Select/AddressSelect.vue';
import { mapGetters } from 'vuex';

export default {
    name: "MyUserAddress",
    components: {
        AddressSelect,
        Pagination
    },
    computed: {
        ...mapGetters([
            'Token', 'UserInfo', 'RoleType', 'HasUserInfo', 'ColumnType', "UserId"
        ])
    },
    data() {


        return {

            searchForm: {},//搜索定义的数据对象

            editModalFormRules: {
                "Name": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "BelongUserId": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "Phone": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                    {
                        validator: (rule, value, callback) => {
                            var reg = /^1[34578]\d{9}$/;
                            if (!value || !reg.test(value)) {
                                callback(new Error('请输入正确的手机号'));
                            }
                            else {
                                callback();
                            }
                        }, trigger: 'blur'
                    },
                ],
                "ProviceCityArea": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],

                "Address": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "Sex": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "HouseNumber": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
            },
            formData: {

            },//保存或者修改定义的数据对象
            editorShow: false,

        };
    },
    created() {

    },
    methods: {
        //返回上一个页面
        goBack() {
            this.$router.go(-1);
        },

        /**
         * 点击新增或者编辑的时候会触发
         */
        async ShowEditModal(Id) {

            let { Data } = await this.$Post(`/UserAddress/Get`, { Id: Id });
            Data.BelongUserId = this.UserId;
            this.formData = Data;

            this.editorShow = true;

        },
        /**
         * 点击保存的时候会触发
         */
        async CreateOrEditForm() {

            this.$refs.editModalForm.validate(async valid => {
                if (valid) {
                    var { Success } = await this.$Post(`/UserAddress/CreateOrEdit`, this.formData);

                    if (Success) {
                        this.editorShow = false;
                        this.$refs.PaginationTableId.Reload(this.searchForm);
                    }
                }
            })
        },
        /**
         * 单个删除的时候会触发
         */
        async ShowDeleteModal(Id) {
            await this.$PostDelete(`/UserAddress/Delete`, { Id: Id });
            this.$refs.PaginationTableId.Reload(this.searchForm);
        },
        /**
         * 批量删除的时候会触发
         */
        async ShowBatchDeleteModal() {
            var ids = this.$refs.PaginationTableId.GetSelectionRow().map(x => x.Id);
            if (ids.length == 0) {
                this.$message.error("你选择需要删除的记录");
                return;
            }
            await this.$PostDelete(`/UserAddress/BatchDelete`, { Ids: ids });

            this.$refs.PaginationTableId.Reload(this.searchForm);
        },
        /**
         * 点击表格搜索按钮会触发
         */
        async SearchClick() {
            this.$refs.PaginationTableId.Reload(this.searchForm);
        },
        /**
         * 点击清空表单会触发
         */
        async ResetClick() {
            this.searchForm = {};
            this.$refs.PaginationTableId.Reload(this.searchForm);
        },




    },
};
</script>
<style scoped lang="scss">
.address-box {
    margin-top: 20px;
}

.header-titlele {
    line-height: 25px;
    color: #333;
    font-weight: 700;
    font-size: 18px;
    margin: 5px 0px;

}

.address-list {
    display: flex;
    flex-wrap: wrap;
    margin-top: 5px;
}



.address-list .address-item {
    position: relative;
    width: 300px;
    height: 170px;
    padding: 12px;
    margin-left: 10px;
    margin-top: 10px;
    color: #666;
    font-size: 12px;
    line-height: 25px;
    background-image: url(../../assets/address-checked.png);
    background-repeat: no-repeat;
    background-size: 100% 170px;
}

.address-list .address-item:hover {
    background-image: url(../../assets/address-checked.png);

}

.address-list .active {
    background-image: url(../../assets/address-checked.png);

}

.address-list .address-item .address-name {
    font-weight: bold;
}

.address-list .address-item .address-phone {
    display: flex;
    justify-content: space-between;
}

.delete-btn {
    position: absolute;
    right: 10px;
    top: 10px;
    width: 24px;
    height: 24px;
    border-radius: 50%;
    background: rgba(0, 0, 0, 0.1);
    display: flex;
    align-items: center;
    justify-content: center;
    cursor: pointer;
    color: #666;

    &:hover {
        background: rgba(0, 0, 0, 0.2);
        color: #f56c6c;
    }
}

.address-content {
    cursor: pointer;
}
</style>