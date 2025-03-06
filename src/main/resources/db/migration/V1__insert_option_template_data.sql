-- V1__insert_option_template_data.sql

CREATE TABLE option_template (
                                 id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                 name VARCHAR(255) NOT NULL,
                                 description VARCHAR(255) NOT NULL,
                                 created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                 updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE option_template_value (
                                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                       option_template_id BIGINT NOT NULL,
                                       option_value VARCHAR(255) NOT NULL,
                                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                       FOREIGN KEY (option_template_id) REFERENCES option_template(id) ON DELETE CASCADE
);

-- 옵션 템플릿 데이터 삽입
INSERT INTO option_template (name, description) VALUES
                                                    ('포장 방식', '상품의 포장 유형을 선택하세요.'),
                                                    ('수량 선택', '상품을 주문할 수량을 선택하세요.'),
                                                    ('재질', '상품의 재질을 선택하세요.'),
                                                    ('색상', '상품의 색상을 선택하세요.'),
                                                    ('크기', '상품의 크기를 선택하세요.'),
                                                    ('인쇄 옵션', '상품에 인쇄할 로고나 텍스트를 선택하세요.'),
                                                    ('사용 용도', '상품의 주된 사용 용도를 선택하세요.'),
                                                    ('보관 방법', '상품의 보관 방법을 선택하세요.'),
                                                    ('추가 구성품', '추가로 제공되는 구성품을 선택하세요.');

-- 옵션 템플릿 값 데이터 삽입
INSERT INTO option_template_value (option_template_id, option_value) VALUES

-- 포장 방식
(1, '일반 포장'),
(1, '선물 포장'),
(1, '벌크 포장'),

-- 수량 선택
(2, '10개'),
(2, '50개'),
(2, '100개'),

-- 재질
(3, '플라스틱'),
(3, '종이'),
(3, '스테인리스'),
(3, '유리'),

-- 색상
(4, '화이트'),
(4, '블랙'),
(4, '레드'),
(4, '블루'),

-- 크기
(5, '소형'),
(5, '중형'),
(5, '대형'),

-- 인쇄 옵션
(6, '로고 인쇄'),
(6, '텍스트 인쇄'),
(6, '없음'),

-- 사용 용도
(7, '테이크아웃'),
(7, '배달'),
(7, '매장 내 사용'),

-- 보관 방법
(8, '실온 보관'),
(8, '냉장 보관'),
(8, '냉동 보관'),

-- 추가 구성품
(9, '숟가락 포함'),
(9, '포크 포함'),
(9, '젓가락 포함')
