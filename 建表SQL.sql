create table t_equip
(
    id          int auto_increment
        primary key,
    name        varchar(50) not null,
    protocol    tinyint     not null comment '设备通信协议',
    tag         varchar(50) not null comment '设备唯一标识',
    project_key int         not null comment '项目表的主键做设备表的外键',
    secret_key  varchar(50) not null comment '设备唯一密钥',
    constraint t_equip_id_uindex
        unique (id),
    constraint t_equip_secret_key_uindex
        unique (secret_key),
    constraint t_equip_tag_uindex
        unique (tag)
);

create table t_user
(
    id       int auto_increment
        primary key,
    name     varchar(50) not null,
    account  varchar(50) not null comment '此列为用户账号, 也是用户邮箱',
    password varchar(50) not null,
    portrait mediumtext  null comment '用户头像(base64)',
    constraint t_user_user_account_uindex
        unique (account),
    constraint t_user_user_id_uindex
        unique (id)
)
    comment '此表为千寻云物联平台用户账户信息表, 存有账号密码及其他表外键';

create table t_article
(
    id            int auto_increment
        primary key,
    title         varchar(100)                         not null,
    publish_time  timestamp  default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '创建时间',
    cover         varchar(1000)                        null comment '封面图片URL',
    view_count    int        default 0                 not null comment '浏览量',
    like_count    int        default 0                 not null comment '点赞数',
    collect_count int        default 0                 not null comment '收藏数',
    title_tag     varchar(50)                          null comment '文章头衔',
    category      varchar(100)                         not null comment '文章分类',
    is_recommend  tinyint(1) default 0                 not null comment '是否推荐',
    user_key      int                                  not null comment '用户/作者id, 做外键',
    content       longtext                             not null comment '文章内容',
    constraint t_article_t_user_id_fk
        foreign key (user_key) references t_user (id)
)
    comment '文章';

create table t_project
(
    id            int auto_increment
        primary key,
    name          varchar(50)  not null,
    industry      tinyint      not null comment '此列表示此项目的行业类型',
    net_work_kind tinyint      not null comment '项目网络工作种类, 项目联网方案',
    project_tag   varchar(50)  not null comment '项目标识码, 项目标签',
    create_date   datetime     not null comment '项目创建时间',
    remark        varchar(100) null comment '项目备注',
    user_key      int          not null comment '此列为t_project表的外键, t_user表的主键',
    constraint t_project_id_uindex
        unique (id),
    constraint t_project_project_tag_uindex
        unique (project_tag),
    constraint t_project_t_user_id_fk
        foreign key (user_key) references t_user (id)
)
    comment '此表为项目表, 用于存储用户创建的项目数据';

create table t_user_article
(
    id            int auto_increment
        primary key,
    user_id       int                  not null,
    article_id    int                  not null,
    like_count    tinyint(1) default 0 not null,
    collect_count tinyint(1) default 0 not null,
    constraint t_user_article_ibfk_1
        foreign key (user_id) references t_user (id),
    constraint t_user_article_ibfk_2
        foreign key (article_id) references t_article (id)
);

create index article_id
    on t_user_article (article_id);

create index user_id
    on t_user_article (user_id);

