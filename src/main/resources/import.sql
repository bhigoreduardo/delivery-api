
-- Estado
INSERT INTO estado (nome) VALUES ('Acre'), ('Alagoas'), ('Amazonas'), ('Amapá'), ('Maranhão');

-- Cidade
INSERT INTO cidade (nome, estado_id) VALUES ('Bujari', 1), ('Capixaba', 1), ('Água Branca', 2), ('Anadia', 2), ('Beruri', 3), ('Borba', 3), ('Cutias', 4), ('Itaubal', 4), ('Oiapoque', 5), ('Pracuúba', 5);

-- Cozinha
INSERT INTO cozinha (nome) VALUES ('Americana'), ('Chinesa'), ('Japonesa'), ('Chilena'), ('Mexicana'), ('Inglesa');

-- Forma Pagamento
INSERT INTO forma_pagamento (descricao) VALUES ('Dinheiro'), ('Cartão de crédito'), ('Pix');

-- Permissao
INSERT INTO permissao (nome, descricao) VALUES ('EDITAR_COZINHAS', 'Permite editar cozinhas'), ('EDITAR_FORMAS_PAGAMENTO', 'Permite criar ou editar formas de pagamento'), ('EDITAR_CIDADES', 'Permite criar ou editar cidades'), ('EDITAR_ESTADOS', 'Permite criar ou editar estados'), ('CONSULTAR_USUARIOS_GRUPOS_PERMISSOES', 'Permite consultar usuários'), ('EDITAR_USUARIOS_GRUPOS_PERMISSOES', 'Permite criar ou editar usuários'), ('EDITAR_RESTAURANTES', 'Permite criar, editar ou gerenciar restaurantes'), ('CONSULTAR_PEDIDOS', 'Permite consultar pedidos'), ('GERENCIAR_PEDIDOS', 'Permite gerenciar pedidos'), ('GERAR_RELATORIOS', 'Permite gerar relatórios');

-- Grupo
INSERT INTO grupo (nome) VALUES ('Gerente'), ('Vendedor'), ('Secretária'), ('Cadastrador');

-- Usuario
INSERT INTO usuario (nome, email, senha) VALUES ('Saitama', 'saitama@email.com', 'cabelos'), ('Genos', 'genos@email.com', 'sensei'), ('Garou', 'garou@email.com', 'malsupremo');

-- Restaurante
INSERT INTO restaurante (nome, taxa_frete, cozinha_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, cidade_id, ativo, aberto) VALUES ('American Burguer', 2.5, 1, '12000456', 'Rua Tiradentes', '102', 'Perto da Escola', 'Centro', 1, true, false), ('Burguer Tuk', 1.5, 2,'12000456', 'Rua Tiradentes', '102', 'Perto da Escola', 'Centro', 2, true, false), ('Yakisoba Xin', 3.5, 3,'12000456', 'Rua Tiradentes', '102', 'Perto da Escola', 'Centro', 3, true, false), ('Comida Caseira', 2.5, 4, '12000456', 'Rua Tiradentes', '102', 'Perto da Escola', 'Centro', 4, true, false), ('Pimenta Hot', 2.5, 5, '12000456', 'Rua Tiradentes', '102', 'Perto da Escola', 'Centro', 5, true, false), ('Batata Inglesa', 2.5, 6, '12000456', 'Rua Tiradentes', '102', 'Perto da Escola', 'Centro', 6, true, false);

-- Produto
INSERT INTO produto (nome, descricao, preco, restaurante_id, ativo) VALUES ('Hamburguer', 'Hamburguer Legal', 19.90, 1, true), ('X-Burguer', 'X-Burguer Legal', 21.90, 1, true), ('Egg-Buguer', 'Egg-Buguer Legal',15.90, 1, true), ('Bacon-Burguer', 'Bacon-Burguer', 23.50, 2, true), ('X-Burguer', 'X-Burguer Legal', 19.50, 2, true), ('Egg-Burguer', 'Egg-Burguer Legal', 16.50, 2, true), ('Yakisoba', 'Yakisoba Legal', 20.60, 3, true), ('Sushi', 'Sushi Legal', 15.60, 3, true), ('Hanabi', 'Hanabi Legal', 12.60, 3, true), ('Prato Feito', 'Prato Feito Legal', 20.60, 4, true), ('Self Service', 'Self Service Legal', 26.60, 4, true), ('Marmita', 'Marmita Legal', 20.60, 4, true), ('Taco', 'Taco Legal', 25.70, 5, true), ('Burrito', 'Burrito Legal', 30.60, 5, true), ('Apimentado', 'Apimentado Legal', 20.60, 5, true), ('Batata Quente', 'Batata Quente Legal', 40.00, 6, true), ('Batata Média', 'Batata Média Legal', 49.90, 6, true), ('Batata Grande', 'Batata Grande Legal', 59.60, 6, true);
