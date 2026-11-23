# Bibliotecas de terceiros

Este projeto pode utilizar três bibliotecas Java obtidas do Maven Central:

- **SQLite JDBC 3.54.1.0** (Xerial): https://github.com/xerial/sqlite-jdbc — Licença Apache-2.0.
- **SLF4J API 2.0.17**: https://www.slf4j.org/ — Licença MIT.
- **SLF4J NOP 2.0.17**: https://www.slf4j.org/ — Licença MIT.

As bibliotecas não são necessárias para a implementação inicial em memória. São necessárias quando a classe `RepositorioSqlite` passa a ser usada.

Se você redistribuir os arquivos JAR baixados (por exemplo, ao publicá-los no GitHub junto com o projeto), preserve os avisos de licença incluídos neles e mantenha as atribuições acima. Veja as licenças oficiais nos repositórios dos respectivos projetos.

O download automático é feito exclusivamente de `repo.maven.apache.org` através de HTTPS, com versões fixas.
