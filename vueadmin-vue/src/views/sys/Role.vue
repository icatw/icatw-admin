<template>
  <div>
    <!--搜索框-->
    <el-form :inline="true" :model="searchForm" class="searchForm">
      <el-form-item>
        <el-input
            v-model="searchForm.name"
            placeholder="名称"
            clearable>
        </el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getRoleList()">搜索</el-button>
        <el-button type="primary" @click="dialogFormVisible = true" v-if="hasAuth('sys:role:save')">新增</el-button>
        <el-popconfirm title="确定要删除这些记录吗？" @confirm="delHandle(null)" style="margin-left: 10px;"
                       v-if="hasAuth('sys:role:delete')">
          <el-button type="danger" slot="reference" :disabled="delBtnStu">批量删除</el-button>
        </el-popconfirm>
      </el-form-item>
    </el-form>
    <!--列表-->
    <el-table
        ref="multipleTable"
        border
        stripe
        :data="tableData"
        tooltip-effect="dark"
        style="width: 100%"
        @selection-change="handleSelectionChange">
      <el-table-column
          type="selection"
          width="55">
      </el-table-column>
      <el-table-column
          prop="name"
          label="名称"
          width="120">
      </el-table-column>
      <el-table-column
          prop="code"
          label="唯一编码"
          width="120">
      </el-table-column>
      <el-table-column
          prop="remark"
          label="描述">
      </el-table-column>
      <el-table-column
          prop="statu"
          label="状态"
          width="120">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.statu === 0" size="small" type="danger">禁用</el-tag>
          <el-tag v-else-if="scope.row.statu === 1" size="small" type="success">正常</el-tag>
        </template>
      </el-table-column>
      <el-table-column
          label="操作"
          width="220">
        <template slot-scope="scope">
          <el-button type="text" @click="permHandle(scope.row.id)" v-if="hasAuth('sys:role:perm')">分配权限</el-button>
          <el-divider direction="vertical"></el-divider>
          <el-button type="text" @click="editHandle(scope.row.id)" v-if="hasAuth('sys:role:update')">编辑</el-button>
          <el-divider direction="vertical"></el-divider>
          <el-popconfirm title="确定要删除这条记录吗？" @confirm="delHandle(scope.row.id)" v-if="hasAuth('sys:role:delete')">
            <el-button type="text" slot="reference">删除</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <!--页码-->
    <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="current"
        :page-sizes="[10, 20, 50, 100]"
        :page-size="size"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
    </el-pagination>
    <!-- 编辑对话框 -->
    <el-dialog title="角色信息" :visible.sync="dialogFormVisible" width="600px" @closed="resetForm('editForm')">
      <el-form :model="editForm" :rules="editFormRules" ref="editForm">
        <el-form-item label="角色名称" prop="name" label-width="100px">
          <el-input v-model="editForm.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="唯一编码" prop="code" label-width="100px">
          <el-input v-model="editForm.code" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="描述" prop="remark" label-width="100px">
          <el-input v-model="editForm.remark" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="状态" prop="statu" label-width="100px">
          <el-radio-group v-model="editForm.statu">
            <el-radio :label="0">禁用</el-radio>
            <el-radio :label="1">正常</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="resetForm('editForm')">取 消</el-button>
        <el-button type="primary" @click="submitEditForm('editForm')">确 定</el-button>
      </div>
    </el-dialog>
    <!-- 分配权限对话框 -->
    <el-dialog title="分配权限" :visible.sync="permDialogFormVisible" width="600px" @closed="resetForm('permForm')">
      <el-form :model="permForm" ref="permForm">
        <el-tree
            :data="permTreeData"
            show-checkbox
            ref="permTree"
            :check-strictly=checkStrictly
            node-key="id"
            :default-expand-all=true
            :props="defaultProps">
        </el-tree>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="resetForm('permForm')">取 消</el-button>
        <el-button type="primary" @click="submitPermForm('permForm')">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "Role",
  data() {
    return {
      searchForm: {
        name: ''
      },
      tableData: [],
      multipleSelection: [],
      dialogFormVisible: false,
      permDialogFormVisible: false,
      delBtnStu: true,
      current: 1,
      total: 0,
      size: 10,
      editForm: {},
      editFormRules: {
        name: [
          {required: true, message: '请输入名称', trigger: 'blur'}
        ],
        code: [
          {required: true, message: '请输入唯一编码', trigger: 'blur'}
        ],
        statu: [
          {required: true, message: '请选择状态', trigger: 'blur'}
        ]
      },
      permForm: {},
      defaultProps: {
        children: 'children',
        label: 'name'
      },
      permTreeData: [],
      treeCheckedKeys: [],
      checkStrictly: true
    }
  },
  methods: {
    toggleSelection(rows) {
      if (rows) {
        rows.forEach(row => {
          this.$refs.multipleTable.toggleRowSelection(row);
        });
      } else {
        this.$refs.multipleTable.clearSelection();
      }
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
      this.delBtnStu = val.length == 0
    },
    getRoleList() {
      this.$axios.get('/sys/role/list', {
        params: {
          name: this.searchForm.name,
          current: this.current,
          size: this.size
        }
      }).then(res => {
        this.tableData = res.data.data.records
        this.current = res.data.data.current
        this.size = res.data.data.size
        this.total = res.data.data.total
        console.log(res)
      })
      this.$axios.get("/sys/menu/list").then(res => {
        this.permTreeData = res.data.data
      })
    },
    handleSizeChange(val) {
      this.size = val
      this.getRoleList()
    },
    handleCurrentChange(val) {
      this.current = val
      this.getRoleList()
    },
    submitEditForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$axios.post('/sys/role/' + (this.editForm.id ? "update" : "save"), this.editForm)
              .then(res => {
                console.log(res.data)
                this.resetForm(formName)
                this.$message({
                  showClose: true,
                  message: '恭喜你，操作成功',
                  type: 'success',
                  onClose: () => {
                    this.getRoleList()
                  }
                });
              })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    editHandle(id) {
      console.log(id)
      this.$axios.get("/sys/role/info/" + id).then(res => {
        this.editForm = res.data.data
        this.dialogFormVisible = true
      })
    },
    delHandle(id) {
      console.log(id)
      var ids = []
      console.log(id ? 31 : 32)
      id ? ids.push(id) : this.multipleSelection.forEach(row => {
        ids.push(row.id)
      })
      console.log(ids)
      this.$axios.post("/sys/role/delete", ids).then(res => {
        console.log(res)
        this.$message({
          showClose: true,
          message: '恭喜你，操作成功',
          type: 'success',
          onClose: () => {
            this.getRoleList()
          }
        });
      })
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
      this.editForm = {}
      this.dialogFormVisible = false
      this.permDialogFormVisible = false
    },
    permHandle(id) {
      this.permDialogFormVisible = true
      this.$axios.get("/sys/role/info/" + id).then(res => {
        this.$refs.permTree.setCheckedKeys(res.data.data.menuIds);
        this.permForm = res.data.data
        console.log("this.treeCheckedKeys")
        console.log(this.treeCheckedKeys)
      })
    },
    submitPermForm(formName) {
      var menuIds = []
      menuIds = this.$refs.permTree.getCheckedKeys()
      // menuIds = menuIds.concat(this.$refs.permTree.getHalfCheckedKeys()) // 半选中状态的父节点
      console.log(menuIds)
      console.log(this.permForm.id)
      this.$axios.post("/sys/role/perm/" + this.permForm.id, menuIds).then(res => {
        this.$message({
          showClose: true,
          message: '恭喜你，操作成功',
          type: 'success',
          onClose: () => {
            this.resetForm(formName)
          }
        });
        this.permDialogFormVisible = false
      })
    },
  },
  created() {
    this.getRoleList()
  }
}
</script>

<style scoped>
.el-pagination {
  float: right;
  margin-top: 22px;
}
</style>
