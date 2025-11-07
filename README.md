# JavaGame2D 🎮

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Status](https://img.shields.io/badge/status-active-success.svg)
![License](https://img.shields.io/badge/license-MIT-blue.svg)

> Motor de jogo 2D multiplayer desenvolvido do zero em Java puro usando JFrame, Canvas e BufferStrategy.

## 📋 Sobre o Projeto

JavaGame2D é uma engine de jogo 2D completa desenvolvida como projeto acadêmico para a disciplina de Programação Orientada a Objetos (POO). O projeto demonstra conceitos avançados de desenvolvimento de jogos, incluindo renderização customizada, física, sistema de animação e networking para multiplayer.

### ✨ Características Principais

- **Engine Customizada**: Sistema de renderização usando Canvas e BufferStrategy para máxima performance
- **Multiplayer em Rede**: Arquitetura cliente-servidor usando sockets TCP
- **Sistema de Física**: Colisões, movimento com aceleração e dash
- **Animações Sprite**: Sistema completo de animação frame a frame
- **Sistema de Partículas**: Emissores de partículas para efeitos visuais (grama, poeira, fogo, slime, etc.)
- **Combat System**: Sistema de ataque com animações e detecção de colisão
- **Gerenciamento de Recursos**: Carregamento e cache de imagens e tiles
- **Interface Gráfica**: Sistema de GUI customizado com elementos interativos

## 🏗️ Arquitetura

### Estrutura do Projeto

```
src/
├── core/           # Núcleo da engine
│   ├── Core.java           # Loop principal do jogo
│   ├── GameFrame.java      # Janela do jogo
│   ├── AnimationTimer.java # Gerenciamento de FPS
│   └── G.java             # Constantes globais
│
├── elements/       # Entidades do jogo
│   ├── Element.java        # Classe base para todos elementos
│   ├── ELM_Player.java     # Jogador com física e animações
│   ├── ELM_Particle.java   # Sistema de partículas
│   ├── ELM_Grass.java      # Elementos de vegetação
│   └── ...
│
├── graphics/       # Sistema de renderização
│   ├── GraphicsFX.java     # Wrapper de gráficos
│   ├── RenderSystem.java   # Gerenciamento de renderização
│   ├── ImageManager.java   # Cache de imagens
│   └── Sprite.java         # Sprites animados
│
├── physics/        # Física do jogo
│   ├── Collider.java       # Sistema de colisão
│   └── Attack.java         # Detecção de ataque
│
├── world/          # Sistema de mundo
│   ├── World.java          # Gerenciamento do mapa
│   └── TileData.java       # Dados de tiles
│
├── server/         # Sistema multiplayer
│   ├── Server.java         # Servidor de jogo
│   ├── Client.java         # Cliente de rede
│   └── ClientHandler.java  # Gerenciamento de conexões
│
├── math/           # Utilitários matemáticos
│   └── Vector.java         # Vetores 2D
│
└── gui/            # Interface de usuário
    └── MENU_Start.java     # Menu inicial
```

### Padrões de Projeto Utilizados

- **Component Pattern**: Sistema de elementos modulares
- **Observer Pattern**: Sistema de eventos e animações
- **Object Pool**: Reutilização de partículas
- **State Pattern**: Estados dos jogadores (idle, correndo, atacando, morto)

## 🎮 Funcionalidades Implementadas

### Sistema de Jogo
- ✅ Loop de jogo com controle de FPS fixo
- ✅ Renderização com double buffering
- ✅ Sistema de câmera
- ✅ Gerenciamento de estados

### Jogador
- ✅ Movimento em 8 direções
- ✅ Sistema de dash
- ✅ Animações de idle, corrida e ataque
- ✅ Sistema de vida e morte
- ✅ Sprites direcionais

### Física e Colisão
- ✅ Colisão AABB (Axis-Aligned Bounding Box)
- ✅ Física baseada em aceleração e velocidade
- ✅ Detecção de colisão com tiles
- ✅ Sistema de hitbox para ataques

### Multiplayer
- ✅ Servidor TCP para múltiplos jogadores
- ✅ Sincronização de posição e estado
- ✅ Sistema de spawn e despawn
- ✅ Tratamento de desconexão

### Efeitos Visuais
- ✅ Partículas de grama, poeira, fogo, slime
- ✅ Efeitos de morte
- ✅ Sombras dinâmicas
- ✅ Sistema de torch animada

## 🚀 Como Executar

### Pré-requisitos
- Java JDK 8 ou superior
- IDE Java (IntelliJ IDEA, Eclipse, NetBeans)

### Executando o Jogo

1. Clone o repositório:
```bash
git clone https://github.com/neverevis/JavaGame2D.git
cd JavaGame2D
```

2. Compile e execute:
```bash
javac src/Main.java
java -Dsun.java2d.opengl=true src.Main
```

### Executando com Servidor (Multiplayer)

1. Inicie o servidor primeiro:
```bash
java src.server.Server
```

2. Configure a conexão no cliente (edite `Client.java`):
```java
socket = new Socket("localhost", 12345);
```

3. Execute o jogo normalmente

## 🎯 Controles

- **WASD** ou **Arrow Keys**: Movimento
- **SPACE**: Dash
- **Mouse**: Atacar
- **ESC**: Menu/Pause

## 📊 Tecnologias e Conceitos

### Java Core
- JFrame e Canvas para renderização
- BufferStrategy para double/triple buffering
- BufferedImage para manipulação de imagens
- Graphics2D para desenho avançado

### Networking
- Socket TCP para comunicação cliente-servidor
- Threads para gerenciamento assíncrono
- Serialização de dados de jogo

### Algoritmos
- Algoritmo de colisão AABB
- Sistema de física baseado em Euler
- Algoritmo de animação sprite sheet
- Sistema de câmera com follow

## 🎓 Aprendizados

Este projeto demonstra:
- Arquitetura de game engine do zero
- Programação orientada a objetos avançada
- Gerenciamento de recursos e performance
- Networking em tempo real
- Sistemas de física e colisão
- Renderização customizada em Java

## 📈 Futuras Melhorias

- [ ] Sistema de inventário
- [ ] Diferentes tipos de inimigos com IA
- [ ] Expansão de Mapa
- [ ] Sistema de save/load
- [ ] Sistema de chat multiplayer

## 👨‍💻 Autor

Desenvolvido por **neverevis** como projeto acadêmico de Programação Orientada a Objetos.
---

⭐ Se este projeto foi útil para você, considere dar uma estrela no repositório!
