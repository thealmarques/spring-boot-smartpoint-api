INSERT INTO `company` (`id`, `company_id`, `modified_date`, `creation_date`, `social_reason`)
VALUES (NULL, '82198127000121', CURRENT_DATE(), CURRENT_DATE(), 'Test IT');

INSERT INTO `employee` (`id`, `person_id`, `modified_date`, `creation_date`, `email`, `name`,
`profile`, `lunch_hours`, `hours_day`, `password`, `price_hour`, `company_id`)
VALUES (NULL, '16248890935', CURRENT_DATE(), CURRENT_DATE(), 'admin@test.com', 'ADMIN', 'ROLE_ADMIN', NULL, NULL,
'$2a$06$xIvBeNRfS65L1N17I7JzgefzxEuLAL0Xk0wFAgIkoNqu9WD6rmp4m', NULL,
(SELECT `id` FROM `company` WHERE `company_id` = '82198127000121'));