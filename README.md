# JavaGame2D

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Status](https://img.shields.io/badge/status-active-success.svg)
![License](https://img.shields.io/badge/license-MIT-blue.svg)

> 2D Multiplayer Game Engine built from scratch in pure Java using JFrame, Canvas and BufferStrategy.

[English](#english) | [Português](#português)

---

## English

### About the Project

JavaGame2D is a complete 2D game engine developed as an academic project for the Object-Oriented Programming (OOP) course. The project demonstrates advanced game development concepts, including custom rendering, physics, sprite animation system, and multiplayer networking.

### Main Features

- **Custom Engine**: Custom rendering system using Canvas and BufferStrategy for maximum performance
- **Multiplayer Networking**: Client-server architecture using TCP sockets
- **Advanced Physics System**: Collisions, acceleration-based movement, and dash mechanics
- **Sprite Animation System**: Complete frame-by-frame animation system
- **Particle System**: Particle emitters for visual effects (grass, dust, fire, slime, etc.)
- **Combat System**: Attack system with animations and collision detection
- **Resource Management**: Image and tile loading and caching
- **Graphical Interface**: Customized GUI system with interactive elements

### Architecture

#### Project Structure

```
src/
├── core/          # Engine core
│   ├── Core.java         # Main game loop
│   ├── GameFrame.java    # Game window
│   ├── AnimationTimer.java  # FPS management
│   └── G.java            # Global constants
│
├── elements/     # Game entities
│   ├── Element.java      # Base class for all elements
│   ├── ELM_Player.java   # Player with physics and animations
│   ├── ELM_Particle.java # Particle system
│   ├── ELM_Grass.java    # Vegetation elements
│   └── ...
│
├── graphics/     # Rendering system
│   ├── GraphicsFX.java   # Graphics wrapper
│   ├── RenderSystem.java # Rendering management
│   ├── ImageManager.java # Image cache
│   └── Sprite.java       # Animated sprites
│
├── physics/      # Game physics
│   ├── Collider.java     # Collision system
│   └── Attack.java       # Attack detection
│
├── world/        # World system
│   ├── World.java        # World management
│   └── TileData.java     # Tile data
│
└── server/       # Multiplayer system
    ├── Server.java       # Game server
    ├── Client.java       # Network client
    └── ClientHandler.java # Connection management
```

### Technologies and Concepts

#### Java Core
- JFrame and Canvas for rendering
- BufferStrategy for double/triple buffering
- BufferedImage for image manipulation
- Graphics2D for advanced drawing

#### Networking
- TCP Socket for client-server communication
- Threads for asynchronous management
- Game data serialization

#### Algorithms
- AABB collision algorithm
- Euler-based physics system
- Sprite sheet animation algorithm
- Camera system with follow

### How to Run

#### Prerequisites
- Java JDK 11 or higher
- Terminal/Command Prompt

#### Running Locally (Single Player)

1. Clone the repository:
```bash
git clone https://github.com/neverevis/JavaGame2D.git
cd JavaGame2D
```

2. Compile and run:
```bash
javac src/Main.java
java -Dsun.java2d.opengl=true src.Main
```

#### Running with Server (Multiplayer)

1. Start the server first:
```bash
java src.server.Server
```

2. Configure the connection in the client terminal by entering the server's IP address

3. Run the game normally

### Controls

- **WASD** or **Arrow Keys**: Movement
- **SPACE**: Dash
- **Mouse**: Attack
- **ESC**: Menu/Pause

### Key Learnings

This project demonstrates:

- Game engine architecture from scratch
- Advanced object-oriented programming
- Resource and performance management
- Real-time networking
- Physics and collision systems
- Custom rendering in Java

### Future Improvements

- [ ] Inventory system
- [ ] Different types of enemies with AI
- [ ] Map expansion
- [ ] Save/load system
- [ ] Multiplayer chat system

### Author

Developed by **neverevis** as an academic project for Object-Oriented Programming.

---

## Português

### Sobre o Projeto

JavaGame2D é uma engine de jogo 2D completa desenvolvida como projeto acadêmico para a disciplina de Programação Orientada a Objetos (POO). O projeto demonstra conceitos avançados de desenvolvimento de jogos, incluindo renderização customizada, física, sistema de animação e networking para multiplayer.

### Características Principais

- **Engine Customizada**: Sistema de renderização usando Canvas e BufferStrategy para máxima performance
- **Multiplayer em Rede**: Arquitetura cliente-servidor usando sockets TCP
- **Sistema de Física Avançado**: Colisões, movimento com aceleração e dash
- **Sistema de Animações Sprite**: Sistema completo de animação frame a frame
- **Sistema de Partículas**: Emissores de partículas para efeitos visuais (grama, poeira, fogo, slime, etc.)
- **Combat System**: Sistema de ataque com animações e detecção de colisão
- **Gerenciamento de Recursos**: Carregamento e cache de imagens e tiles
- **Interface Gráfica**: Sistema de GUI customizado com elementos interativos

### Arquitetura

#### Estrutura do Projeto

```
src/
├── core/          # Núcleo da engine
│   ├── Core.java         # Loop principal do jogo
│   ├── GameFrame.java    # Janela do jogo
│   ├── AnimationTimer.java  # Gerenciamento de FPS
│   └── G.java            # Constantes globais
│
├── elements/     # Entidades do jogo
│   ├── Element.java      # Classe base para todos elementos
│   ├── ELM_Player.java   # Jogador com física e animações
│   ├── ELM_Particle.java # Sistema de partículas
│   ├── ELM_Grass.java    # Elementos de vegetação
│   └── ...
│
├── graphics/     # Sistema de renderização
│   ├── GraphicsFX.java   # Wrapper de gráficos
│   ├── RenderSystem.java # Gerenciamento de renderização
│   ├── ImageManager.java # Cache de imagens
│   └── Sprite.java       # Sprites animados
│
├── physics/      # Física do jogo
│   ├── Collider.java     # Sistema de colisão
│   └── Attack.java       # Detecção de ataque
│
├── world/        # Sistema de mundo
│   ├── World.java        # Gerenciamento de mapa
│   └── TileData.java     # Dados de tiles
│
└── server/       # Sistema multiplayer
    ├── Server.java       # Servidor de jogo
    ├── Client.java       # Cliente de rede
    └── ClientHandler.java # Gerenciamento de conexões
```

### Tecnologias e Conceitos

#### Java Core
- JFrame e Canvas para renderização
- BufferStrategy para double/triple buffering
- BufferedImage para manipulação de imagens
- Graphics2D para desenho avançado

#### Networking
- Socket TCP para comunicação cliente-servidor
- Threads para gerenciamento assíncrono
- Serialização de dados de jogo

#### Algoritmos
- Algoritmo de colisão AABB
- Sistema de física baseado em Euler
- Algoritmo de animação sprite sheet
- Sistema de câmera com follow

### Como Executar

#### Pré-requisitos
- Java JDK 11 ou superior
- Terminal/Prompt de Comando

#### Executando Localmente (Single Player)

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

#### Executando com Servidor (Multiplayer)

1. Inicie o servidor primeiro:
```bash
java src.server.Server
```

2. Configure a conexão no terminal do cliente inserindo o endereço IP do servidor

3. Execute o jogo normalmente

### Controles

- **WASD** ou **Setas**: Movimento
- **SPACE**: Dash
- **Mouse**: Atacar
- **ESC**: Menu/Pause

### Aprendizados

Este projeto demonstra:

- Arquitetura de game engine do zero
- Programação orientada a objetos avançada
- Gerenciamento de recursos e performance
- Networking em tempo real
- Sistemas de física e colisão
- Renderização customizada em Java

### Futuras Melhorias

- [ ] Sistema de inventário
- [ ] Diferentes tipos de inimigos com IA
- [ ] Expansão de Mapa
- [ ] Sistema de save/load
- [ ] Sistema de chat multiplayer

### Autor

Desenvolvido por **neverevis** como projeto acadêmico de Programação Orientada a Objetos.

---

⭐ If this project was useful to you, consider giving it a star on the repository! | Se este projeto foi útil para você, considere dar uma estrela no repositório!
