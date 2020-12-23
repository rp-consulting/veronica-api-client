-- https://app.camunda.com/jira/browse/CAM-9920
ALTER TABLE ACT_HI_OP_LOG
    ADD COLUMN CATEGORY_ varchar(64);

ALTER TABLE ACT_HI_OP_LOG
    ADD COLUMN EXTERNAL_TASK_ID_ varchar(64);