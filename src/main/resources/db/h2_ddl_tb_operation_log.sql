
create table tb_operation_log (
   `id`          bigint           NOT NULL  AUTO_INCREMENT ,
   `message`     varchar(2000)    NOT NULL                 ,
   `is_deleted`  TINYINT          NOT NULL  DEFAULT 0      ,
   `created_by`  bigint           NOT NULL                 ,
   `updated_by`  bigint                                    ,
   `created_at`  bigint           NOT NULL                 ,
   `updated_at`  bigint                                    ,

    PRIMARY KEY (`id`)
);