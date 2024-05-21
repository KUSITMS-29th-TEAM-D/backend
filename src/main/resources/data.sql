-- 테스트 유저 삽입
INSERT INTO users (id, name, provider, provider_id, users_uuid, created_date, updated_date)
VALUES (100001, '테스트1', 'kakao', '3471511962', UNHEX(REPLACE('159f4542-ebff-4acd-a603-a4fb4c94526a', '-', '')), LOCALTIMESTAMP, LOCALTIMESTAMP),
       (100002, '테스트2', 'kakao', '3471511963', UNHEX(REPLACE('159f4542-ebff-4acd-a603-a4fb4c94526b', '-', '')), LOCALTIMESTAMP, LOCALTIMESTAMP)
ON DUPLICATE KEY UPDATE name = VALUES(name);

-- 테스트 유저 온보딩 정보 삽입
INSERT INTO users_onboarding_infos (users_id, nickname, job, understanding_score, profile_img_url, created_date, updated_date) VALUES
                                             (100001, '테스트1', '개발자', '100', '1234asdf', LOCALTIMESTAMP, LOCALTIMESTAMP),
                                             (100002, '테스트2', '개발자', '100', '1234asdf', LOCALTIMESTAMP, LOCALTIMESTAMP)
ON DUPLICATE KEY UPDATE nickname = VALUES(nickname);

-- 테스트 유저 관심분야 정보 삽입
INSERT INTO users_interests (id, users_id, interests_id, created_date, updated_date) VALUES
                                             (100001, 100001, 1, LOCALTIMESTAMP, LOCALTIMESTAMP),
                                             (100002, 100002, 1, LOCALTIMESTAMP, LOCALTIMESTAMP)
ON DUPLICATE KEY UPDATE id = VALUES(id);

-- 테스트 유저 키워드 정보 삽입
INSERT INTO users_keywords (id, users_id, keywords_id, created_date, updated_date) VALUES
                                             (100001, 100001, 1, LOCALTIMESTAMP, LOCALTIMESTAMP),
                                             (100002, 100002, 1, LOCALTIMESTAMP, LOCALTIMESTAMP)
ON DUPLICATE KEY UPDATE id = VALUES(id);

-- 관심 분야 전체 삽입
INSERT INTO interests (interests_id, name) VALUES
                                               (1, '드로잉'),
                                               (2, '공예'),
                                               (3, '요리'),
                                               (4, '음악'),
                                               (5, '운동'),
                                               (6, '라이프스타일'),
                                               (7, '사진/영상'),
                                               (8, '금융/재테크'),
                                               (9, '창업/부업'),
                                               (10, '프로그래밍'),
                                               (11, '데이터사이언스'),
                                               (12, 'AI'),
                                               (13, '제2외국어'),
                                               (14, '뷰티'),
                                               (15, '테크/디지털'),
                                               (16, '심리/상담'),
                                               (17, '여행'),
                                               (18, '자기계발'),
                                               (19, '기타')
ON DUPLICATE KEY UPDATE interests_id = VALUES(interests_id);

-- 이미지 키워드 전체 삽입
INSERT INTO keywords (keywords_id, name) VALUES
                                             (1, '긍정적인'),
                                             (2, '열정적인'),
                                             (3, '책임있는'),
                                             (4, '따스한'),
                                             (5, '감정적인'),
                                             (6, '공감능력이 좋은'),
                                             (7, '창의적인'),
                                             (8, '도전적인'),
                                             (9, '헌신적인'),
                                             (10, '계산적인'),
                                             (11, '수학적인'),
                                             (12, '믿을만한'),
                                             (13, '애정있는'),
                                             (14, '소통하는'),
                                             (15, '결단력 있는'),
                                             (16, '소극적인'),
                                             (17, '외부 영향을 많이 받는')
ON DUPLICATE KEY UPDATE keywords_id = VALUES(keywords_id);