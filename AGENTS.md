# Regras para Comandos do OpenSpec

Ao atuar como assistente neste repositório e receber comandos do OpenSpec (como `/opsx:propose`), aplique as seguintes regras:

- **Versionamento de Nomes:** Sempre que eu solicitar a criação de uma nova feature, bug ou ajuste, você deve OBRIGATORIAMENTE iniciar o nome do diretório/proposta com `v` seguido da data atual no formato `vYYYYMMDD-`.
- **Ordem Obrigatória:** O prefixo de versão/data deve vir antes da ação e da descrição: `vYYYYMMDD-<action>-<description>`.
- **Exemplo Prático:** Se eu pedir "crie uma proposta para arrumar o layout", você deve processar e executar o comando como `/opsx:propose v20260512-fix-layout` (utilizando a data de hoje).
- **Não Inverta a Ordem:** Nunca use nomes como `fix-v20260512-subscription-plan-autocomplete-noop`; o correto é `v20260512-fix-subscription-plan-autocomplete-noop`.
- **Compatibilidade com OpenSpec:** Use sempre o prefixo `v` antes da data para manter ordenação cronológica e satisfazer ferramentas que exigem nomes começando com letra.
- Não crie nomes genéricos (evite `wip`, `update`, `test`). Prefira ações diretas (`add-`, `fix-`, `refactor-`).

## Versionamento de libs compartilhadas
- Nunca commitar aumento manual de versão das libs compartilhadas Vevox (`shared-*`, `shared-infra-*`, `shared-components`) em `pom.xml`, `package.json`, lockfiles ou manifests de consumidores.
- O bump/publicação de versão oficial é responsabilidade do pipeline da própria lib após merge/push na `main`.
- Para teste local, usar versão com sufixo fixo `-local` e não commitar esse sufixo nem alterações transitórias de versionamento.
- Fluxo correto: alterar a lib compartilhada, testar localmente com `-local` quando necessário, commitar/push apenas o código da lib, aguardar o pipeline gerar a versão oficial e só então atualizar consumidores para a versão publicada.
