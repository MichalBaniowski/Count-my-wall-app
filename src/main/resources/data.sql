INSERT INTO material_categories (description, name) VALUES ('materiał używany do tworzenia elementów kontrukcyjnych', 'rdzeń'); --1
INSERT INTO material_categories (description, name) VALUES ('materiał używany do izolacji termincznej lub akustycznej elementów budowlanych', 'izolacja akustyczna/termiczna'); --2
INSERT INTO material_categories (description, name) VALUES ('materiał używany do izolacji przeciw wodnej/wilgociowej i/lub parochronnej', 'izolacja przeciwwodna'); --3
INSERT INTO material_categories (description, name) VALUES ('materiał używany do wykończenia elementu budowlanego', 'wykończenie'); --4
INSERT INTO material_categories (description, name) VALUES ('inne zastosowania', 'inne'); --5

INSERT INTO composite_types (name) VALUES ('ściana zewnętrzna'); --1
INSERT INTO composite_types (name) VALUES ('ściana wewnętrzna'); --2
INSERT INTO composite_types (name) VALUES ('strop'); --3
INSERT INTO composite_types (name) VALUES ('stropodach'); --4
INSERT INTO composite_types (name) VALUES ('dach stromy'); --5
INSERT INTO composite_types (name) VALUES ('płyta fundamentowa'); --6
INSERT INTO composite_types (name) VALUES ('strop nad nieogrzewaną piwnicą'); --7

INSERT INTO building_materials (name, thermal_conductivity, steam_transfer_coefficient) VALUES ('żelbet', 2.2, 30); --1
INSERT INTO building_materials (name, thermal_conductivity, steam_transfer_coefficient) VALUES ('beton z kruszywa wapiennego', 0.72, 150); --2
INSERT INTO building_materials (name, thermal_conductivity, steam_transfer_coefficient) VALUES ('mur z bloczków z betonu komórkowego', 0.29, 150); --3
INSERT INTO building_materials (name, thermal_conductivity, steam_transfer_coefficient) VALUES ('płyta pilśniowa twarda', 0.18, 20); --4
INSERT INTO building_materials (name, thermal_conductivity, steam_transfer_coefficient) VALUES ('marmur, granit', 3.5, 7.5); --5
INSERT INTO building_materials (name, thermal_conductivity, steam_transfer_coefficient) VALUES ('mur z cegły ceramicznej pełnej na zaprawie cementowo-wapiennej', 0.77, 105); --6
INSERT INTO building_materials (name, thermal_conductivity, steam_transfer_coefficient) VALUES ('mur z cegły kratówki na zaprawie cementowo-wapiennej', 0.56, 150); --7
INSERT INTO building_materials (name, thermal_conductivity, steam_transfer_coefficient) VALUES ('wełna mineralna luzem', 0.043, 480); --8
INSERT INTO building_materials (name, thermal_conductivity, steam_transfer_coefficient) VALUES ('płyty z wełny mineralnej', 0.042, 480); --9
INSERT INTO building_materials (name, thermal_conductivity, steam_transfer_coefficient) VALUES ('styropian', 0.04, 12); --10
INSERT INTO building_materials (name, thermal_conductivity) VALUES ('pianka poliuretanowa', 0.032); --11
INSERT INTO building_materials (name, thermal_conductivity, steam_transfer_coefficient) VALUES ('tynk cementowy', 1, 45); --12
INSERT INTO building_materials (name, thermal_conductivity, steam_transfer_coefficient) VALUES ('tynk cementowo - wapienny', 0.82, 45); --13
INSERT INTO building_materials (name, thermal_conductivity, steam_transfer_coefficient) VALUES ('tynk wapienny', 0.7, 75); -- 14
INSERT INTO building_materials (name, thermal_conductivity) VALUES ('papa asfaltowa', 0.18); --15

INSERT INTO building_materials_categories (building_material_id, categories_id) VALUES (1, 1);
INSERT INTO building_materials_categories (building_material_id, categories_id) VALUES (2, 4);
INSERT INTO building_materials_categories (building_material_id, categories_id) VALUES (2, 1);
INSERT INTO building_materials_categories (building_material_id, categories_id) VALUES (3, 1);
INSERT INTO building_materials_categories (building_material_id, categories_id) VALUES (4, 4);
INSERT INTO building_materials_categories (building_material_id, categories_id) VALUES (4, 5);
INSERT INTO building_materials_categories (building_material_id, categories_id) VALUES (5, 1);
INSERT INTO building_materials_categories (building_material_id, categories_id) VALUES (5, 4);
INSERT INTO building_materials_categories (building_material_id, categories_id) VALUES (5, 5);
INSERT INTO building_materials_categories (building_material_id, categories_id) VALUES (6, 1);
INSERT INTO building_materials_categories (building_material_id, categories_id) VALUES (6, 4);
INSERT INTO building_materials_categories (building_material_id, categories_id) VALUES (7, 1);
INSERT INTO building_materials_categories (building_material_id, categories_id) VALUES (7, 4);
INSERT INTO building_materials_categories (building_material_id, categories_id) VALUES (8, 2);
INSERT INTO building_materials_categories (building_material_id, categories_id) VALUES (9, 2);
INSERT INTO building_materials_categories (building_material_id, categories_id) VALUES (10, 2);
INSERT INTO building_materials_categories (building_material_id, categories_id) VALUES (11, 2);
INSERT INTO building_materials_categories (building_material_id, categories_id) VALUES (12, 4);
INSERT INTO building_materials_categories (building_material_id, categories_id) VALUES (13, 4);
INSERT INTO building_materials_categories (building_material_id, categories_id) VALUES (14, 4);
INSERT INTO building_materials_categories (building_material_id, categories_id) VALUES (15, 3);

INSERT INTO composite_materials(building_material_id, thickness, material_diffusion_resistance, material_heat_resistance) VALUES (1, 0.25, 0.0083, 0.114); --1
INSERT INTO composite_materials(building_material_id, thickness, material_diffusion_resistance, material_heat_resistance) VALUES (2, 0.3, 0.0017, 0.417); --2
INSERT INTO composite_materials(building_material_id, thickness, material_diffusion_resistance, material_heat_resistance) VALUES (3, 0.3, 0.002, 1.034); --3
INSERT INTO composite_materials(building_material_id, thickness, material_diffusion_resistance, material_heat_resistance) VALUES (6, 0.36, 0.0034, 0.467); --4
INSERT INTO composite_materials(building_material_id, thickness, material_diffusion_resistance, material_heat_resistance) VALUES (7, 0.36, 0.0024, 0.643); --5
INSERT INTO composite_materials(building_material_id, thickness, material_diffusion_resistance, material_heat_resistance) VALUES (9, 0.1, 0.00021, 2.38); --6
INSERT INTO composite_materials(building_material_id, thickness, material_diffusion_resistance, material_heat_resistance) VALUES (10, 0.15, 0.0125, 3.75); --7
INSERT INTO composite_materials(building_material_id, thickness, material_diffusion_resistance, material_heat_resistance) VALUES (10, 0.2, 0.0167, 5); --8
INSERT INTO composite_materials(building_material_id, thickness, material_diffusion_resistance, material_heat_resistance) VALUES (12, 0.015, 0.0003, 0.015); --9
INSERT INTO composite_materials(building_material_id, thickness, material_diffusion_resistance, material_heat_resistance) VALUES (14, 0.015, 0.0002, 0.021); --10
INSERT INTO composite_materials(building_material_id, thickness, material_heat_resistance) VALUES (15, 0.005, 0.028); --11

INSERT INTO composites(composite_type_id, name, description, composite_diffusion_resistance, composite_sum_of_heat_resistance, composite_heat_transfer_coefficient) VALUES
(1,'SZ_01','wielowarstwowa ściana zewnętrzna w konstrukcji żelbetowej', 0.0214, 4.054, 0.2467),
(1,'SZ_02','wielowarstwowa ściana zewnętrzna w konstrukcji ceramicznej', 0.0197, 5.843, 0.1711),
(1,'SZ_03','wielowarstwowa ściana zewnętrzna w konstrukcji z bloczków z betonu komórkowego', 0.0149, 4.826, 0.2072);

COMMIT;