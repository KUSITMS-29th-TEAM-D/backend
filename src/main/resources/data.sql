-- 관심 분야 전체 삽입
INSERT INTO interests (interests_uuid, name) VALUES
                                                 (UNHEX(REPLACE('d20e2654-3c4a-4ebe-b1c9-5695ac2a6207', '-', '')), '드로잉'),
                                                 (UNHEX(REPLACE('fe96c294-b5f3-425e-a6de-8cc1b13beb5a', '-', '')), '공예'),
                                                 (UNHEX(REPLACE('118ccbfb-8caf-498b-913a-16a315b3a859', '-', '')), '요리'),
                                                 (UNHEX(REPLACE('4a0db2eb-f4bc-4fa3-ae47-8381ed0da1ab', '-', '')), '음악'),
                                                 (UNHEX(REPLACE('ae4a3cee-f7e3-48a1-8b0a-eb4d177b2267', '-', '')), '운동'),
                                                 (UNHEX(REPLACE('1f479a8d-dab2-4d95-96c9-73d5f7382a01', '-', '')), '라이프스타일'),
                                                 (UNHEX(REPLACE('8969e7f1-2d1e-4a6d-b234-73c2aa7b24ff', '-', '')), '사진/영상'),
                                                 (UNHEX(REPLACE('9b11a16b-6786-4a28-8273-ff9e06b80318', '-', '')), '금융/제테크'),
                                                 (UNHEX(REPLACE('35009d25-65e1-48da-800e-44be42bf3b4e', '-', '')), '창업/부업'),
                                                 (UNHEX(REPLACE('775f2020-070f-4ba1-b601-b456b4a8c165', '-', '')), '프로그래밍'),
                                                 (UNHEX(REPLACE('5b3a7d95-529d-42a4-a9eb-9e3fd3c42933', '-', '')), '데이터사이언스'),
                                                 (UNHEX(REPLACE('e8e34cc1-27e1-4875-b474-90c3c1c2a7bb', '-', '')), 'AI'),
                                                 (UNHEX(REPLACE('c27a3a02-134b-41de-a50e-27d722d2fbbd', '-', '')), '제2외국어'),
                                                 (UNHEX(REPLACE('f9abf63a-bfd3-4960-840e-45841a1c50d3', '-', '')), '뷰티'),
                                                 (UNHEX(REPLACE('3c0c90ff-f775-474a-bc6d-83f1c8c30536', '-', '')), '테크/디지털'),
                                                 (UNHEX(REPLACE('8a3eb59d-263e-486a-aa9c-c672d2599a8b', '-', '')), '심리/상담'),
                                                 (UNHEX(REPLACE('45633dc1-34d0-4a2c-8cc9-0bf7ecbb6e3c', '-', '')), '여행'),
                                                 (UNHEX(REPLACE('8954a54d-2e9a-4a6e-b63e-081119c4a93c', '-', '')), '자기계발'),
                                                 (UNHEX(REPLACE('89fde358-fd6b-4d6b-ba07-057e6c4e4b8b', '-', '')), '기타')
ON DUPLICATE KEY UPDATE interests_uuid = interests_uuid;

-- 이미지 키워드 전체 삽입
INSERT INTO keywords (keywords_uuid, name) VALUES
                                               (UNHEX(REPLACE('05e59ff6-7d1c-4497-850a-1683de7e7e59', '-', '')), '긍정적인'),
                                               (UNHEX(REPLACE('1b2f7f85-5d71-4881-81ad-70a1b2d1c1a0', '-', '')), '열정적인'),
                                               (UNHEX(REPLACE('61715019-1f05-45e6-91e2-13b50d818efb', '-', '')), '책임있는'),
                                               (UNHEX(REPLACE('b06da443-52c2-4398-9bdf-6a7f3f14f29f', '-', '')), '따스한'),
                                               (UNHEX(REPLACE('c5a5ff7b-0b40-4683-b796-5c295b1908a5', '-', '')), '감정적인'),
                                               (UNHEX(REPLACE('ec2b0244-e37c-4fd0-8aee-c11c831124b3', '-', '')), '공감능력이 좋은'),
                                               (UNHEX(REPLACE('7f372388-f2c0-4a3c-b183-3486b9a21732', '-', '')), '창의적인'),
                                               (UNHEX(REPLACE('f080d5f5-4e70-4d9c-9d27-8ddbb1602ad4', '-', '')), '도전적인'),
                                               (UNHEX(REPLACE('2c6c11e8-39f0-4e7a-a8f7-569f6e891b71', '-', '')), '헌신적인'),
                                               (UNHEX(REPLACE('d9dc29b1-5fb1-49f3-baeb-16e4644a3d03', '-', '')), '계산적인'),
                                               (UNHEX(REPLACE('7f339058-6e22-4b1f-81c1-4c8edf892eaf', '-', '')), '수학적인'),
                                               (UNHEX(REPLACE('f4378d43-9fcf-48b1-96cf-cf8d0cb74836', '-', '')), '믿을만한'),
                                               (UNHEX(REPLACE('ad8109ad-24f1-4e64-aa1c-d55a2b572b05', '-', '')), '애정있는'),
                                               (UNHEX(REPLACE('e31f28ed-f089-474f-a429-3a8c9e1c8244', '-', '')), '소통하는'),
                                               (UNHEX(REPLACE('ac3e1cb2-035f-4a16-92b3-eb8d1a8d73c2', '-', '')), '결단력 있는'),
                                               (UNHEX(REPLACE('bc438d76-f0cf-4bcb-9a8a-0dcd83ad5706', '-', '')), '소극적인'),
                                               (UNHEX(REPLACE('46349d0f-99fd-40f0-9eb9-215c97c72925', '-', '')), '외부 영향을 많이 받는')
ON DUPLICATE KEY UPDATE keywords_uuid = keywords_uuid;