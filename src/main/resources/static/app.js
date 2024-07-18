const BASE_URL_API = 'http://localhost:8080/api';
const BASE_URL = 'http://localhost:8080/';

document.addEventListener('DOMContentLoaded', () => {
    const loginForm = document.getElementById('login-form');
    const registerForm = document.getElementById('register-form');
    const taskForm = document.getElementById('task-form');
    const tasksSection = document.getElementById('tasks-section');
    const authSection = document.getElementById('auth-section');
    const tasksList = document.getElementById('tasks-list');

    let authToken = '';

    loginForm.addEventListener('submit', async (event) => {
        event.preventDefault();
        const username = document.getElementById('login-username').value;
        const password = document.getElementById('login-password').value;

        try {
            const response = await fetch(`${BASE_URL}/auth/login`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ username, password })
            });

            if (response.ok) {
                const data = await response.json();
                authToken = data.jwt;
                authSection.classList.add('hidden');
                tasksSection.classList.remove('hidden');
                await fetchTasks();
            } else {
                alert('Login failed');
            }
        } catch (error) {
            console.error('Error:', error);
        }
    });

    registerForm.addEventListener('submit', async (event) => {
        event.preventDefault();
        const username = document.getElementById('register-username').value;
        const password = document.getElementById('register-password').value;

        try {
            const response = await fetch(`${BASE_URL}/auth/register`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ username, password })
            });

            if (response.ok) {
                alert('Registration successful');
            } else {
                alert('Registration failed');
            }
        } catch (error) {
            console.error('Error:', error);
        }
    });

    taskForm.addEventListener('submit', async (event) => {
        event.preventDefault();
        const name = document.getElementById('task-name').value;
        const description = document.getElementById('task-description').value;

        try {
            const response = await fetch(`${BASE_URL_API}/tasks`, {
            method: 'POST',
                headers: {
                'Content-Type': 'application/json',
                    'Authorization': Bearer ${authToken}
            },
            body: JSON.stringify({ name, description, isCompleted: false, steps: [] })
        });

        if (response.ok) {
            await fetchTasks();
        } else {
            alert('Failed to add task');
        }
    } catch (error) {
        console.error('Error:', error);
    }
});

async function fetchTasks() {
    try {
        const response = await fetch(`${BASE_URL_API}/tasks`, {
        headers: { 'Authorization': Bearer ${authToken} }
    });

    if (response.ok) {
        const tasks = await response.json();
        displayTasks(tasks);
    } else {
        alert('Failed to fetch tasks');
    }
} catch (error) {
    console.error('Error:', error);
}
}

function displayTasks(tasks) {
    tasksList.innerHTML = '';
    tasks.forEach(task => {
        const taskDiv = document.createElement('div');
        taskDiv.classList.add('task');

        const taskName = document.createElement('h3');
        taskName.textContent = task.name;

        const taskDescription = document.createElement('p');
        taskDescription.textContent = task.description;

        const taskCompleted = document.createElement('p');
        taskCompleted.textContent = `Completed: ${task.isCompleted}`;

        const stepsDiv = document.createElement('div');
        task.steps.forEach(step => {
            const stepDiv = document.createElement('div');
            stepDiv.classList.add('step');

            const stepName = document.createElement('p');
            stepName.textContent = `Step: ${step.name}`;

            const stepDescription = document.createElement('p');
            stepDescription.textContent = `Description: ${step.description}`;

            stepDiv.appendChild(stepName);
            stepDiv.appendChild(stepDescription);
            stepsDiv.appendChild(stepDiv);
        });

        taskDiv.appendChild(taskName);
        taskDiv.appendChild(taskDescription);
        taskDiv.appendChild(taskCompleted);
        taskDiv.appendChild(stepsDiv);

        tasksList.appendChild(taskDiv);
    });
}
});