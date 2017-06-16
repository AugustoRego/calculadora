# Calculadora Socket-RMI 

### Objetivo
* Este Projeto tem a finalidade de compor a nota da disciplina 'Sistemas Distribuídos'

#### Funcionalidades :

| Feature                                                                                                                        | Status |
|--------------------------------------------------------------------------------------------------------------------------------|--------|
|um servidor concorrente principal que receberá requisições via socket e encaminhará as mesmas, via RMI para servidores escravos |   ok   |
|servidores escravos que se registrarão no servidor principal e serão encarregados de realizar as operações                      |   ok   |
|algoritimo para decisão para escolha do servidor escravo (sugestão round-robin)                                                 |   ok   |
|servidores escravos especializados que receberão requisições específicas, delegadas pelos servidor principal                    |   ok   |
|clientes que farão requisições para o servidor principal                                                                        |   ok   |
|As 4 operações básicas devem ser executadas nos servidores escravos básicos                                                     |   ok   |
|As operações de Porcentagem, raiz quadrada devem ser executadas nos servidores escravos especiais                               |   ok   |
|(EXTRA) Se não houver servidores escravos cadastrados então o servidor principal realizará o cálculo                            |   ok   |
 
  
<p align="center">
  <img src="composite.png" width="100%"/>  
</p>
 
### Decisões do projeto
 
 #### Servidor Principal
  * é possível informar a porta utilizada pelo socket
  * Inicia o serviço RMI na porta fixa = 1099
  * abre um frame de logs
  
 #### Servidor Escravo
  * é possível informar o tipo do mesmo, sendo ele Especial ou Básico
  * após informado o tipo, solicitará apenas host/ip do servidor principal, pois a porta do mesmo é fixa = 1099
  * solicitará a porta irá usar
  * abre um frame de logs
 
 #### Cliente
  * solicitará o host/ip do servidor principal
  * solicitará a porta (socket) do servidor principal
  * abrirá uma tela com multiplas escolhas de operações
  * pedirá os valores para as mesmas
  
### Passos para compilar
  ```bash
  mvn package
  ```
  * serão criados 3 jars ( "Servidor-Principal.jar", "Cliente.jar" e "Servidor-Escravo.jar" )

  ### Passos para rodar
  * 1- iniciar o Servidor-Principal.jar
  * 2- iniciar o Servidor-Escravo.jar (quantos desejar)
  * 3- iniciar o Cliente.jar ( quantos desejar)
  
  ### Referencias
  * [Round Robin](https://imasters.com.br/banco-de-dados/simplicidade-e-importancia-round-robin-como-tecnica-de-balanceamento/?trace=1519021197&source=single)
  * [Java RMI](http://docs.oracle.com/javase/7/docs/technotes/guides/rmi/hello/hello-world.html)
  * [Java Socket](https://www.google.com.br/url?sa=t&rct=j&q=&esrc=s&source=web&cd=1&cad=rja&uact=8&ved=0ahUKEwjdx6bGpsLUAhVBI5AKHQCGAqIQFggiMAA&url=https%3A%2F%2Fwww.caelum.com.br%2Fapostila-java-orientacao-objetos%2Fapendice-sockets%2F&usg=AFQjCNH4BySB7boDWjlaq2Y2H3DgD4esMw)
  
