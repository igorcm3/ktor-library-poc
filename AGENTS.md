# Regras para Comandos do OpenSpec

Ao atuar como assistente neste repositório e receber comandos do OpenSpec (como `/opsx:propose`), aplique as seguintes regras:

- Não crie nomes genéricos (evite `wip`, `update`, `test`). Prefira ações diretas (`add-`, `fix-`, `refactor-`).

## Versionamento de libs compartilhadas
- Nunca commitar aumento manual de versão das libs compartilhadas Vevox (`shared-*`, `shared-infra-*`, `shared-components`) em `pom.xml`, `package.json`, lockfiles ou manifests de consumidores.
- O bump/publicação de versão oficial é responsabilidade do pipeline da própria lib após merge/push na `main`.
- Para teste local, usar versão com sufixo fixo `-local` e não commitar esse sufixo nem alterações transitórias de versionamento.
- Fluxo correto: alterar a lib compartilhada, testar localmente com `-local` quando necessário, commitar/push apenas o código da lib, aguardar o pipeline gerar a versão oficial e só então atualizar consumidores para a versão publicada.
