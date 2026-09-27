CREATE TABLE tb_despesas (
                             id_despesa bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                             data_despesa date NOT NULL,
                             titulo varchar(255) NOT NULL,
                             valor numeric(10, 2) NOT NULL,
                             descricao varchar(255)
)
