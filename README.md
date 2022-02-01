# devboot

## 概要

- Spring Bootアプリケーションを、VSCodeの拡張機能`Remote Containers`を利用して良い感じに開発するためのテンプレート
- AP-DBの2層アーキテクチャ

## 全体構成

![architecture](images/architecture.drawio.svg)

| No  | Function           | Component   | Base Image                 | Container Name             |
| --: | ------------------ | ----------- | -------------------------- | -------------------------- |
|   1 | Application Server | Spring Boot | openjdk:17.0.2-slim-buster | devboot_devcontainer_app_1 |
|   2 | Database Server    | PostgreSQL  | postgres:13.3-alpine       | devboot_devcontainer_db_1  |

### アプリケーション構成

| No  | Function        | Component    |  Version |
| --: | --------------- | ------------ | -------: |
|   1 | JDK             | OpenJDK      |   17.0.2 |
|   2 | Framework       | Spring Boot  |    2.6.3 |
|   3 | Template Engine | Thymeleaf    |        - |
|   4 | O/R Mapper      | MyBatis      |        - |

## 主な設定

### コンテナ上での作業ディレクトリ

接続先での作業ディレクトリの指定を行う。
- `.devcontainer/devcontainer.json`の以下を編集する。

```json
    "workspaceFolder": "/devboot",
```

### VSCodeの拡張機能

接続先での拡張機能設定を行う。  
- `.devcontainer/devcontainer.json`の以下を編集する。
- 必要な拡張機能の`Identifier`を配列として記述する。

```json
    "extensions": [
        "vscjava.vscode-java-pack",
        "pivotal.vscode-boot-dev-pack",
        "gabrielbb.vscode-lombok",
        // omit...
    ]
```

### VSCodeの基本設定

接続先での基本設定の上書きを行う。
- `.devcontainer/devcontainer.json`の以下を編集する。
- `JAVA_HOME`系は上書き必須

```json
    "settings": {
        "java.jdt.ls.java.home": "/usr/local/openjdk-17",
        // omit...
    }
```

### ローカルmvnリポジトリのマウント

ホストマシンのローカルmvnリポジトリの設定をする
- コンテナにローカルmvnリポジトリをマウントする場合、`.devcontainer/.env`ファイルを作成し、以下を記述する

  ```sh
  HOME=/home/vscode
  ```

- コンテナにローカルmvnリポジトリをマウントする場合、`.devcontainer/docker-compose.yml`から以下を削除する

  ```yml
    # ホストのMavenローカルリポジトリ
    - ${HOME}/.m2:/root/.m2
  ```

## コンテナの起動

1. VSCodeの左下から以下アイコンをクリックし、`Open Folder in Container...`を選択  
   ![remote_container_icon](images/remote_container_icon.jpg)
2. コンテナのビルドが開始されるので、しばらく待つ
3. VSCode内でコンテナ上のディレクトリが開かれる

## アプリケーション開発

### デバッグ

```sh
./mvnw spring-boot:run
```

### テスト

```sh
./mvnw clean test -Dmaven.test.failure.ignore=true surefire-report:report
```

### 本番用資材ビルド&実行

```sh
./mvnw clean package -Dmaven.test.skip=true
java -jar target/devboot-${version}.jar
```

## Reference

- [devcontainer.json reference](https://code.visualstudio.com/docs/remote/devcontainerjson-reference)
