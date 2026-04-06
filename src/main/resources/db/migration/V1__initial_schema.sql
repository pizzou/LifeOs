CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY, name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE, password VARCHAR(255) NOT NULL,
    avatar_url VARCHAR(500), timezone VARCHAR(100) DEFAULT 'UTC',
    premium BOOLEAN DEFAULT FALSE, onboarded BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT NOW(), updated_at TIMESTAMP DEFAULT NOW()
);
CREATE TABLE IF NOT EXISTS categories (
    id BIGSERIAL PRIMARY KEY, user_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
    name VARCHAR(100) NOT NULL, type VARCHAR(50) NOT NULL,
    color VARCHAR(20) DEFAULT '#6366f1', icon VARCHAR(50) DEFAULT 'circle',
    created_at TIMESTAMP DEFAULT NOW()
);
CREATE TABLE IF NOT EXISTS tasks (
    id BIGSERIAL PRIMARY KEY, user_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
    category_id BIGINT REFERENCES categories(id) ON DELETE SET NULL,
    parent_id BIGINT REFERENCES tasks(id) ON DELETE CASCADE,
    title VARCHAR(500) NOT NULL, description TEXT,
    priority VARCHAR(20) DEFAULT 'MEDIUM', status VARCHAR(20) DEFAULT 'TODO',
    due_date TIMESTAMP, completed_at TIMESTAMP, estimated_mins INTEGER,
    recurrence_rule VARCHAR(255), is_recurring BOOLEAN DEFAULT FALSE,
    pinned BOOLEAN DEFAULT FALSE, archived BOOLEAN DEFAULT FALSE, position INTEGER DEFAULT 0,
    created_at TIMESTAMP DEFAULT NOW(), updated_at TIMESTAMP DEFAULT NOW()
);
CREATE TABLE IF NOT EXISTS accounts (
    id BIGSERIAL PRIMARY KEY, user_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
    name VARCHAR(255) NOT NULL, type VARCHAR(50) NOT NULL,
    currency VARCHAR(10) DEFAULT 'USD', balance DOUBLE PRECISION DEFAULT 0,
    color VARCHAR(20) DEFAULT '#10b981', is_default BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT NOW(), updated_at TIMESTAMP DEFAULT NOW()
);
CREATE TABLE IF NOT EXISTS transactions (
    id BIGSERIAL PRIMARY KEY, user_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
    account_id BIGINT REFERENCES accounts(id) ON DELETE CASCADE,
    category_id BIGINT REFERENCES categories(id) ON DELETE SET NULL,
    type VARCHAR(20) NOT NULL, amount DOUBLE PRECISION NOT NULL,
    currency VARCHAR(10) DEFAULT 'USD', description VARCHAR(500),
    notes TEXT, date DATE NOT NULL, is_recurring BOOLEAN DEFAULT FALSE,
    recurrence_rule VARCHAR(255),
    created_at TIMESTAMP DEFAULT NOW(), updated_at TIMESTAMP DEFAULT NOW()
);
CREATE TABLE IF NOT EXISTS budgets (
    id BIGSERIAL PRIMARY KEY, user_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
    category_id BIGINT REFERENCES categories(id) ON DELETE CASCADE,
    name VARCHAR(255) NOT NULL, amount DOUBLE PRECISION NOT NULL,
    period VARCHAR(20) DEFAULT 'MONTHLY', start_date DATE NOT NULL,
    end_date DATE, alert_threshold INTEGER DEFAULT 80,
    created_at TIMESTAMP DEFAULT NOW(), updated_at TIMESTAMP DEFAULT NOW()
);
CREATE TABLE IF NOT EXISTS habits (
    id BIGSERIAL PRIMARY KEY, user_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
    category_id BIGINT REFERENCES categories(id) ON DELETE SET NULL,
    name VARCHAR(255) NOT NULL, description TEXT,
    icon VARCHAR(50) DEFAULT 'check', color VARCHAR(20) DEFAULT '#6366f1',
    frequency VARCHAR(20) DEFAULT 'DAILY', frequency_days VARCHAR(50),
    target_count INTEGER DEFAULT 1, target_unit VARCHAR(50) DEFAULT 'times',
    is_active BOOLEAN DEFAULT TRUE, start_date DATE DEFAULT CURRENT_DATE,
    archived BOOLEAN DEFAULT FALSE, current_streak INTEGER DEFAULT 0,
    longest_streak INTEGER DEFAULT 0,
    created_at TIMESTAMP DEFAULT NOW(), updated_at TIMESTAMP DEFAULT NOW()
);
CREATE TABLE IF NOT EXISTS habit_logs (
    id BIGSERIAL PRIMARY KEY, habit_id BIGINT REFERENCES habits(id) ON DELETE CASCADE,
    user_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
    log_date DATE NOT NULL, count INTEGER DEFAULT 1, notes VARCHAR(500),
    created_at TIMESTAMP DEFAULT NOW(), UNIQUE(habit_id, log_date)
);
CREATE TABLE IF NOT EXISTS goals (
    id BIGSERIAL PRIMARY KEY, user_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
    category_id BIGINT REFERENCES categories(id) ON DELETE SET NULL,
    title VARCHAR(500) NOT NULL, description TEXT,
    type VARCHAR(50) DEFAULT 'PERSONAL', status VARCHAR(20) DEFAULT 'ACTIVE',
    target_value DOUBLE PRECISION, current_value DOUBLE PRECISION DEFAULT 0,
    unit VARCHAR(50), start_date DATE NOT NULL, due_date DATE, completed_at TIMESTAMP,
    color VARCHAR(20) DEFAULT '#6366f1', icon VARCHAR(50) DEFAULT 'target',
    created_at TIMESTAMP DEFAULT NOW(), updated_at TIMESTAMP DEFAULT NOW()
);
CREATE TABLE IF NOT EXISTS notes (
    id BIGSERIAL PRIMARY KEY, user_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
    category_id BIGINT REFERENCES categories(id) ON DELETE SET NULL,
    title VARCHAR(500), content TEXT,
    is_pinned BOOLEAN DEFAULT FALSE, is_archived BOOLEAN DEFAULT FALSE,
    color VARCHAR(20) DEFAULT '#ffffff',
    created_at TIMESTAMP DEFAULT NOW(), updated_at TIMESTAMP DEFAULT NOW()
);
CREATE TABLE IF NOT EXISTS reminders (
    id BIGSERIAL PRIMARY KEY, user_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
    task_id BIGINT REFERENCES tasks(id) ON DELETE CASCADE,
    title VARCHAR(500) NOT NULL, description TEXT,
    remind_at TIMESTAMP NOT NULL, timezone VARCHAR(100),
    recurrence_rule VARCHAR(255), is_recurring BOOLEAN DEFAULT FALSE,
    channel VARCHAR(50) DEFAULT 'PUSH', dismissed BOOLEAN DEFAULT FALSE,
    sent BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT NOW(), updated_at TIMESTAMP DEFAULT NOW()
);
CREATE INDEX IF NOT EXISTS idx_tasks_user   ON tasks(user_id);
CREATE INDEX IF NOT EXISTS idx_tasks_due    ON tasks(due_date) WHERE archived = FALSE;
CREATE INDEX IF NOT EXISTS idx_tx_user      ON transactions(user_id);
CREATE INDEX IF NOT EXISTS idx_tx_date      ON transactions(date);
CREATE INDEX IF NOT EXISTS idx_habits_user  ON habits(user_id);
CREATE INDEX IF NOT EXISTS idx_habit_logs   ON habit_logs(habit_id, log_date);
CREATE INDEX IF NOT EXISTS idx_goals_user   ON goals(user_id);
CREATE INDEX IF NOT EXISTS idx_notes_user   ON notes(user_id);
