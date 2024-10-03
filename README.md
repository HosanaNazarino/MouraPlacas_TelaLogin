# Moura Placas - Tela de Login

## Estrutura do Projeto

### 1. **MainActivity**
   - Esta é a tela de login da aplicação.
   - Contém campos para o usuário inserir seu nome de usuário e senha.
   - Um botão que, ao ser clicado, verifica se os campos estão preenchidos e chama o método de login.
   - O método de `login` faz uma requisição HTTP para autenticar o usuário.

### 2. **RecyclerViewActivity**
   - Após o login bem-sucedido, esta atividade é chamada para exibir uma lista de placas.
   - Usa `RecyclerView` para apresentar os dados de forma eficiente.
   - Faz uma chamada de rede para obter as placas e atualiza a interface do usuário com os dados recebidos.

### 3. **PlateAdapter**
   - Adapter para gerenciar a lista de placas no `RecyclerView`.
   - Define como cada item da lista será exibido.

### 4. **PhotoActivity**
   - Permite ao usuário capturar fotos.
   - Possui botões que abrem a câmera para tirar fotos.

### 5. **PhotoEvent**
   - Classe de evento utilizada para transmitir a imagem capturada entre atividades.

### 6. **SharedPreferencesUtils**
   - Classe utilitária para gerenciar dados armazenados nas `SharedPreferences`, permitindo salvar e recuperar informações como o nome de usuário e ID da loja.

## Layouts XML
- **activity_main.xml**: Contém a interface da tela de login, incluindo campos de texto e um botão.
- **activity_recycler_view.xml**: Layout para a tela que exibe as placas.
- **item_place.xml**: Layout para cada item na lista de placas.
- **photo_activity.xml**: Layout para a atividade de captura de fotos.

## Observações Importantes
- **Inacabado**: O projeto ainda está em desenvolvimento. Algumas funcionalidades podem não estar completamente implementadas.
- **Não testada**: A aplicação não foi testada em dispositivos reais, o que significa que podem ocorrer erros durante a execução.
- **Possíveis erros**: O código contém algumas seções que podem não funcionar corretamente, como chamadas de rede e tratamento de eventos. Por exemplo, a manipulação das respostas da API e a lógica de captura de fotos precisam de mais atenção.

