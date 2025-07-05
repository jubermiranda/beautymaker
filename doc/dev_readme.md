# Project Development Guide

Welcome to the development team!

This document contains the setup instructions and development workflow you should follow when contributing to the project.

## 🚀 Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/jubermiranda/beautymaker
cd beautymaker
```

---

### 2. Branch Workflow

We use the following branching model:

- **`main`**: production-ready, stable code
- **`develop`**: active development branch

#### How to contribute:

1. Make sure you're on the `develop` branch:
   ```bash
   git checkout develop
   ```
2. Make your changes and push:
   ```bash
   git push
   ```
3. Open a Pull Request (not needed, but recommended)
   [Tip:]
    If you just pushed the branch, GitHub will usually show a banner like:

    > _“Compare & pull request”_

    ✅ You can click that button to quickly open a PR into the `develop` branch.

---

## 🧱 Database Setup

The project uses **PostgreSQL** as its database.

### 1. Start the database with Docker

A `docker-compose.yml` file is included in the project root. To start the PostgreSQL container:

```bash
docker compose up -d
```

This will start a PostgreSQL server with the configuration defined in the compose file.

---

### 2. Configure environment variables

You must create a `.env` file in the root of the project with your database credentials.

Example:

```env
DATABASE_URL=...
DATABASE_USER=...
DATABASE_PASS=...
```

---

## 📦 Additional Notes

- Migrations and schema setup scripts are stored in the `db/` directory.
- Please make sure to test your changes locally before opening a PR.

---

If you have any questions, reach out to a maintainer or ask in the dev channel.

Happy coding! 🚀
