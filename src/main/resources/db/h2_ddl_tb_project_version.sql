
create table tb_project_version (
   `id`          bigint           NOT NULL  AUTO_INCREMENT ,
   `version`     varchar(100)     NOT NULL                 ,
   `is_deleted`  TINYINT          NOT NULL  DEFAULT 0      ,
   `created_by`  bigint           NOT NULL                 ,
   `updated_by`  bigint                                    ,
   `created_at`  bigint           NOT NULL                 ,
   `updated_at`  bigint                                    ,

    PRIMARY KEY (`id`)
);