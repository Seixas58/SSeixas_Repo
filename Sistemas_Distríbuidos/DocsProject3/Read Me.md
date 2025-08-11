READ ME

Para o Projeto 3, decidimos fazer as seguintes us:
1.As a librarian, I want to create a Book, Author and Genre in the same process
 (implies at least two services).
2.As a reader, upon returning a Book, I want to recommend it (positively or
 negatively). It implies a service for Recommendations.

As us que fizemos para este trabalho são as seguintes: [UCD.md](UCD.md).

Para a realização deste trabalho foi necessário fazer bastantes gráficos, como por exemplo Vl, VF, UCD, DM, SD.

Primeiramemte e mais importante é sabermos o as entidades usadas, assim como os seus atributos e ligações, para isso é preciso 
observar com atenção o domain model [DM.puml](DM.svg). 

Para a realização da vista física [VF.puml](VF.svg), foi usado o protocolo SQL/TCP entre databases, onde o SQL serviu para gerenciar e executar diretamente na base de dados,
enquanto que o protocolo TCP foi necessário para garantir a comunicação entre os servidores, uma vez que este protocolo 
fornece bastante confiabilidade.
O protocolo AMQP entre databases e o Message Broker foi utilizado para facilitar a troca de mensagens e é deveras importante 
para a integração dentre vários sistemas diferentes possíveis como também permite que produtos diferentes que implementem este protocolo 
possam trocar informações.

O projeto 3 consistia na realização das duas us ditas anteriormente, assim sendo, as us tinham como objetivo:
1.Criar um livro, author e genero no mesmo processo, para isso foi necessário verificar se o livro já se encontrava 
na base de dados dos livros, caso exista dá conflito, no entanto, caso não esteja na base de dados, é criado um livro temporário 
juntamente com o author, onde o author para ser diferente, tem que ter um isbn e uma descriçao diferente da do resto para ser criado.
2.Para haver uma recomendação do livro é necessário haver uma lending, e que esteja logado como um reader, basicamente o pedido é feito 
através do isbn do livro em questao que está na lending, assim como a descrição e o rating de 0 a 5, caso tente meter um valor superior a 5, dá erro.

Os SD feitos são extremamente valiosos, devido a cada nível ter a sua importância.
Relativamente aos SD-N1, são geralmente feitos para os clientes mostrando apenas as principais mensagens para realizar uma certa tarefa.[SD-N1-CreateUser.puml](SD-N1-CreateUser.svg)
Já no SD-N2, mostra como os serviços ou subsistemas comunicam entre si, sendo útil para para um alvo que já perceba mais do assunto entendendo assim melhor o fluxo entre módulos.[SD-N2-CreateUser.puml](SD-N2-CreateUser.svg)
Por fim o SD-N3, fornece um nível técnico bastante detalhado e completo, mostrando classes, métodos e serviços específicos. Mostra a chamada de métodos e
o fluxo de mensagens, sendo este sequence diagram usado para desenvolvedores ou equipas técnicas que precisam de informações detalhadas para implementar no sistema.[SD-N3-CreateUser.puml](SD-N3-CreateUser.svg)



