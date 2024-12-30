-- 더미 데이터 생성
DELIMITER $$

CREATE PROCEDURE generate_dummy_item()
BEGIN
    DECLARE v_counter INT DEFAULT 0;

    -- 100,000번 반복하여 데이터 삽입
    WHILE v_counter < 100000 DO
            INSERT INTO item (brand, category, funding_count, item_image_url, like_count, item_name, item_price, review_count)
            VALUES (
                       CONCAT('Brand', FLOOR(RAND() * 10) + 1),
                       CONCAT('Category', FLOOR(RAND() * 5) + 1),
                       0,
                       CONCAT('https://example.com/image', FLOOR(RAND() * 1000) + 1, '.jpg'),
                       0,
                       CONCAT('Item', FLOOR(RAND() * 1000) + 1),
                       10000 + FLOOR(RAND() * 10000),
                       0
                   );

            SET v_counter = v_counter + 1;
        END WHILE;
END $$

DELIMITER ;

-- 생성한 프로시저 호출
CALL generate_dummy_item();
