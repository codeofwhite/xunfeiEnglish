const CryptoJS = require('crypto-js')
var request = require('request')
var log = require('log4node')

// 系统配置
const config = {
    // 请求地址
    hostUrl: "https://itrans.xfyun.cn/v2/its",
    host: "itrans.xfyun.cn",
    //在控制台-我的应用-机器翻译获取
    appid: "30fa122e",
    //在控制台-我的应用-机器翻译获取
    apiSecret: "Mzg2ZDdjODJjYWM1MzE5M2ZkM2E0NGJl",
    //在控制台-我的应用-机器翻译获取
    apiKey: "96b41233a0974ee46f365d13ef6bea09",
    uri: "/v2/its"
}

let transVar = {
    text: "你好世界！",//待翻译文本
    from: "cn",//源语种
    to: "en"//目标语种
}

// 获取当前时间 RFC1123格式
let date = (new Date().toUTCString())
let postBody = getPostBody(transVar.text, transVar.from, transVar.to)
let digest = getDigest(postBody)

let options = {
    url: config.hostUrl,
    headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json,version=1.0',
        'Host': config.host,
        'Date': date,
        'Digest': digest,
        'Authorization': getAuthStr(date, digest)
    },
    json: true,
    body: postBody
}

request.post(options, (err, resp, body) => {
    if (err) {
        log.error('error ' + err)
    }
    if (body.code != 0) {
        log.error(`发生错误，错误码：${body.code}错误原因：${body.message}`)
    }
    log.info(`sid：${body.sid}`)
    log.info(`原文：[${body.data.result.from}] ${body.data.result.trans_result.src}`)
    log.info(`译文：[${body.data.result.to}] ${body.data.result.trans_result.dst}`)
})

// 生成请求body
function getPostBody(text, from, to) {
    let digestObj = {
        //填充common
        common: {
            app_id: config.appid
        },
        //填充business
        business: {
            from: from,
            to: to
        },
        //填充data
        data: {
            text: CryptoJS.enc.Base64.stringify(CryptoJS.enc.Utf8.parse(text))
        }
    }
    return digestObj
}

// 请求获取请求体签名
function getDigest(body) {
    return 'SHA-256=' + CryptoJS.enc.Base64.stringify(CryptoJS.SHA256(JSON.stringify(body)))
}

// 鉴权签名
function getAuthStr(date, digest) {
    let signatureOrigin = `host: ${config.host}\ndate: ${date}\nPOST ${config.uri} HTTP/1.1\ndigest: ${digest}`
    let signatureSha = CryptoJS.HmacSHA256(signatureOrigin, config.apiSecret)
    let signature = CryptoJS.enc.Base64.stringify(signatureSha)
    let authorizationOrigin = `api_key="${config.apiKey}", algorithm="hmac-sha256", headers="host date request-line digest", signature="${signature}"`
    return authorizationOrigin
}