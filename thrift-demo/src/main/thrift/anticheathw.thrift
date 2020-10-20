namespace cpp ysec.anti_cheat_hw
namespace java ysec.anti_cheat_hw

// 提供第三方登陆平台的账号类型
enum AssociatedAccountType
{
	YY    = 0,    // YY平台账号
	QQ    = 1,    // QQ帐号
	WEXIN = 2,    // 微信账号
	WEIBO = 3,    // 微博账号
	INVALID = 255   // 其他未知账号类型
}

enum Dimension
{
	ALL      = 0x0,     // 默认选取全部维度
	ANTI_SDK = 0x1,     // 实时外挂防刷检测
	PERSONA  = 0x2,     // 离线用户画像
	IP       = 0x4,     // IP画像
	DEVICE   = 0x8,     // 设备画像
	PHONE    = 0x10     // 手机号码画像
	EMAIL    = 0x20     // 邮箱
}

// 用户终端类型
enum TermType
{
	ALL      = 0,
	PC       = 1,
	ANDROID  = 2,
	IOS      = 3,
	WEB      = 4,
	INVALID  = 255
}

struct RequestItem
{
	1: string uid,              // 业务内部系统所用的用户唯一标识
	2: i32 termType = 0,      // 用户终端类型 (请根据TermType选取有效值)
	3: string ip,               // 用户出口IP (选填)
	4: string device,           // 用户设备标识 (选填)
	5: string phone,            // 用户电话号码 (选填)
	6: string email,            // 用户邮箱 (选填)
	7: AssociatedAccountType assocAcctType = 255, // 用户登陆所使用的第三方登陆平台类型(选填)
	8: string assocAcctId,      // 第三方账号平台返回的账号唯一标识 (如微信/QQ平台返回的openId,选填)
	9: string extension         // 额外提供的参数以json的形式在此字段提供，目前支持的关键字详情参见文档(选填)
}
struct AuthorizeMsg
{
    1: string AuthUser;
    2: string AuthKey;
    3: map<string,string>keyvalue;
}



struct Request
{
	1: AuthorizeMsg authMsg,
	2: string appId,              // 业务方标识
	3: string token,              // 业务方秘钥
	4: i64 dimension,             // 查询维度
	5: list<RequestItem> items    // 支持批量查询，但请求项不得超过50个
}

struct ResponseItem
{
	1: string uid,
	2: i32 riskLevel,   // 风险等级，0 - 100
	3: string detail,    // 评分细则和其他信息(json)
	4: AssociatedAccountType assocAcctType, // 如请求时使用关联账号 assocAcctType/assocAcctId 会填请求时相应的值
	5: string assocAcctId
}

struct Response
{
	1: i32 rescode; // 返回码: 0 表示成功，非0表示不成功；当不成功时 msg 字段携带错误详情
	2: map<string,string>keyvalue;
	3: string desc,      // 错误信息(仅在返回码不为0时有效)
	4: list<ResponseItem> items  // 查询结果
}

struct RequestEx
{
	1: AuthorizeMsg authMsg,
	2: string appId,              // 业务方标识
	3: string token,              // 业务方秘钥
	4: string uidIdentity;       // uid身份标识
	5: string subAppid;           //支付subappid
	6: i64 dimension = 0,         // 查询维度
	7: string uid,              // 业务内部系统所用的用户唯一标识
	8: i32 termType = 0,      // 用户终端类型 (请根据TermType选取有效值)
	9: string ip,               // 用户出口IP (选填)
	10: string device,           // 用户设备标识 (选填)
	11: string phone,            // 用户电话号码 (选填)
	12: string email,            // 用户邮箱 (选填)
	13: AssociatedAccountType assocAcctType = 255, // 用户登陆所使用的第三方登陆平台类型(选填)
	14: string assocAcctId,      // 第三方账号平台返回的账号唯一标识 (如微信/QQ平台返回的openId,选填)
	15: string extension         // 额外提供的参数以json的形式在此字段提供，目前支持的关键字详情参见文档(选填)
}

struct ResponseEx
{
	1: i32 rescode; // 返回码: 0 表示成功，非0表示不成功；当不成功时 msg 字段携带错误详情
	2: map<string,string>keyvalue;
	3: string desc,      // 错误信息(仅在返回码不为0时有效)
	4: string uid,
	5: i32 riskLevel,   // 风险等级，0 - 100
	6: string detail,    // 评分细则和其他信息(json)
	7: AssociatedAccountType assocAcctType, // 如请求时使用关联账号 assocAcctType/assocAcctId 会填请求时相应的值
	8: string assocAcctId
}

service AntiCheatService
{
	Response riskLevelQueryHw(1:Request request)	
	ResponseEx riskLevelQueryHwEx(1:RequestEx request)
}
