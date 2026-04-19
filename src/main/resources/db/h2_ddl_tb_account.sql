
create table tb_account (
   `id`          bigint           NOT NULL  AUTO_INCREMENT ,
   `user_id`     bigint           NOT NULL                 ,
   `coin`        varchar(20)      NOT NULL                 ,
   `amount`      decimal(38,18)   NOT NULL                 ,
   `is_deleted`  TINYINT          NOT NULL  DEFAULT 0      ,
   `created_by`  bigint           NOT NULL                 ,
   `updated_by`  bigint                                    ,
   `created_at`  bigint           NOT NULL                 ,
   `updated_at`  bigint                                    ,

    PRIMARY KEY (`id`)
);