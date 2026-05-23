IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='usuarios' AND xtype='U')
CREATE TABLE usuarios (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    role VARCHAR(30) NOT NULL,
    cep VARCHAR(10),
    logradouro VARCHAR(255),
    bairro VARCHAR(100),
    cidade VARCHAR(100),
    uf VARCHAR(2)
);

IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='restaurantes' AND xtype='U')
CREATE TABLE restaurantes (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao VARCHAR(255),
    categoria VARCHAR(100),
    imagem_url VARCHAR(500),
    dono_id BIGINT NOT NULL,
    CONSTRAINT fk_restaurante_dono FOREIGN KEY (dono_id) REFERENCES usuarios(id)
);

IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='categorias' AND xtype='U')
CREATE TABLE categorias (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    nome VARCHAR(100) NOT NULL UNIQUE
);

IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='produtos' AND xtype='U')
CREATE TABLE produtos (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao VARCHAR(255),
    preco FLOAT NOT NULL,
    imagem_url VARCHAR(500),
    categoria_id BIGINT NOT NULL,
    restaurante_id BIGINT NOT NULL,
    CONSTRAINT fk_produto_categoria FOREIGN KEY (categoria_id) REFERENCES categorias(id),
    CONSTRAINT fk_produto_restaurante FOREIGN KEY (restaurante_id) REFERENCES restaurantes(id)
);

IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='pedidos' AND xtype='U')
CREATE TABLE pedidos (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    status VARCHAR(30) NOT NULL,
    total FLOAT NOT NULL,
    criado_em DATETIME NOT NULL,
    cliente_id BIGINT NOT NULL,
    restaurante_id BIGINT NOT NULL,
    CONSTRAINT fk_pedido_cliente FOREIGN KEY (cliente_id) REFERENCES usuarios(id),
    CONSTRAINT fk_pedido_restaurante FOREIGN KEY (restaurante_id) REFERENCES restaurantes(id)
);

IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='itens_pedido' AND xtype='U')
CREATE TABLE itens_pedido (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    quantidade INT NOT NULL,
    preco_unitario FLOAT NOT NULL,
    pedido_id BIGINT NOT NULL,
    produto_id BIGINT NOT NULL,
    CONSTRAINT fk_item_pedido FOREIGN KEY (pedido_id) REFERENCES pedidos(id),
    CONSTRAINT fk_item_produto FOREIGN KEY (produto_id) REFERENCES produtos(id)
);
