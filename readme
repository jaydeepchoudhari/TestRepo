for (int i = 0; i < employees.size(); i += BATCH_SIZE) {
        int end = Math.min(i + BATCH_SIZE, employees.size());
        List<EmployeeStageEntity> batch = employees.subList(i, end);
        employeeStgRepository.saveAll(batch);
        employeeStgRepository.flush(); // Flush after each batch
    }
