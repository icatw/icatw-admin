// 引入mockjs
const Mock = require('mockjs')
// 获取 mock.Random 对象
// 参考：https://github.com/nuysoft/Mock/wiki/Mock.Random
const Random = Mock.Random
let Result = {
    code: 200,
    msg: '操作成功',
    data: null
}
/**
 * Mock.mock( url, post/get , function(options))；
 * url 表示需要拦截的 URL，
 * post/get 需要拦截的 Ajax 请求类型
 *
 * 用于生成响应数据的函数
 */
// 获取验证码图片base64编码以及一个随机码
Mock.mock('/captcha', 'get', () => {
    Result.data = {
        token: Random.string(32), // 获取一个32位的随机字符串,
        captchaImg: Random.dataImage("120x40", "nw4kj") //生成验证码为11111的base64图片编码
    }
    return Result
})

Mock.mock('/login', 'post', () => {
    //无法在header中传入jwt
    Result.code = 400
    Result.msg = "验证码错误"
    return Result
})

Mock.mock('/sys/userInfo', 'get', () => {
    Result.data = {
        id: "1",
        username: "test",
        avatar: "http://qzapp.qlogo.cn/qzapp/101983660/4697C6ECF9F91EBE2FF983A6D03F536D/100"
    }
    return Result
})
Mock.mock('/logout', 'post', () => {
    Result.code = 200
    return Result
})
Mock.mock('/sys/menu/nav', 'get', () => {
    let nav = [{
        name: 'SysManga',
        title: '系统管理',
        icon: 'el-icon-s-operation',
        component: '',
        path: '',
        children: [
            {
                name: 'SysUser',
                title: '用户管理',
                icon: 'el-icon-s-custom',
                path: '/sys/users',
                component: 'sys/User',
                children: []
            }
        ]
    },
        {
            name: 'SysTools',
            title: '系统工具',
            icon: 'el-icon-s-tools',
            path: '',
            children: [
                {
                    name: 'SysDict',
                    title: '数字字典',
                    icon: 'el-icon-s-order',
                    path: '/sys/dicts',
                    component: '',
                    children: []
                },
            ]
        }]
    let authoritys = []
    Result.data = {
        nav: nav,
        authoritys: authoritys
    }
    return Result
})
