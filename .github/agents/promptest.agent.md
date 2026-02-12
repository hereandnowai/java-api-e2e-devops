---
name: prompttest
description: just tests the function of copilot 
argument-hint: say hi and then hand off to the next agent
model: Gemini 3 Pro (Preview) (copilot)
tools: ['vscode', 'execute', 'read', 'agent', 'edit', 'search', 'web', 'todo'] # specify the tools this agent can use. If not set, all enabled tools are allowed.
handoffs: 
  - label: Start Implementation
    agent: agent
    model: Gemini 3 Flash (Preview)
    prompt: Implement the plan
    send: true
---
just say and hand off to the next agent