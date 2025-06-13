# 🚶 Walk Recording System 【WALK MEMORY】

<img alt="Walk Memory" src="wrs.png" width="500px">

WALK MEMORY　はユーザーの散歩ルートを自動で記録・可視化するアプリケーションです。手軽に位置情報を取得し、後からマップ上で振り返ることができます。

## 📂 ディレクトリ構成

```plain
wrs-project/
├─ backend/         # Kotlin + Spring Boot API
│  ├─ src/main/     # アプリケーションソース
│  ├─ src/test/     # テストリソース・コード
│  ├─ build.gradle.kts
│  └─ …
├─ frontend/        # React + Vite + Capacitor クライアント
│  ├─ src/          # Reactコンポーネントとアセット
│  ├─ public/       # 静的ファイル
│  ├─ package.json
│  └─ …
└─ README.md        # 本ファイル
````

## 🔍 アプリ概要

散歩開始から終了までの位置情報を30秒ごとに自動取得し、Webマップ上に経路として描画

* 散歩の開始・停止ボタンで計測制御
* 位置データをサーバーに保存し、個別Walkごとに履歴管理
* マイページで過去の散歩履歴を一覧・地図可視化
  


## 🚀 起動方法

1. リポジトリをクローン

   ```bash
   git clone https://github.com/ユーザー名/wrs.git
   cd wrs-project
   ```
2. フロントエンド依存パッケージをインストール

   ```bash
   cd frontend
   npm install 
   ```

   ```
3. データベース（Postgres）を起動し、データベースを作成

   ```sql
   CREATE DATABASE wrs_db;
   ```

4. アプリを起動

   * **バックエンド**

     ```bash
     cd backend
     ./gradlew bootRun
     ```
   * **フロントエンド**

     ```bash
     cd frontend
     npm run dev   # または yarn dev
     ```
5. ブラウザで `http://localhost:5173` にアクセス


