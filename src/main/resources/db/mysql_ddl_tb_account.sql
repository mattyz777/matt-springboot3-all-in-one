
create table tb_account (
   `id`          bigint           NOT NULL  AUTO_INCREMENT     COMMENT  '主键ID',
   `user_id`     bigint           NOT NULL                     COMMENT  '用户ID',
   `coin`        varchar(20)      NOT NULL                     COMMENT  '币种 如BTC',
   `amount`      decimal(38,18)   NOT NULL                     COMMENT  '余额',
   `is_deleted`  TINYINT          NOT NULL  DEFAULT 0          COMMENT  '逻辑删除：0正常 1删除',
   `created_by`  bigint           NOT NULL                     COMMENT  '创建人',
   `updated_by`  bigint                                        COMMENT  '更新人',
   `created_at`  bigint           NOT NULL                     COMMENT  '创建时间',
   `updated_at`  bigint                                        COMMENT  '更新时间',

    PRIMARY KEY (`id`),
   KEY `idx_user_id` (`user_id`)
); ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='账户余额表';